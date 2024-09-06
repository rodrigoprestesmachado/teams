/**
 * @License
 * Copyright 2025 Orion Services @ https://orion-services.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.orion.teams.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a team entity in the system.
 *
 * A team has a unique hash, a creation date, and a list of users who are
 * members of the team. This class extends {@link PanacheEntity} to inherit
 * standard database operations. The {@link Entity} annotation indicates
 * that this class is a JPA entity, and the {@link Getter} and {@link Setter}
 * annotations are used to automatically generate getter and setter methods
 * for the fields.
 */
@Entity
@Getter
@Setter
public class Team extends PanacheEntity {

    /**
     * The unique hash identifier for the team.
     */
    private String hash;

    /**
     * The date and time when the team was created.
     * Automatically set to the current time upon instantiation.
     */
    private LocalDateTime creationDate;

    /**
     * A list of users that are members of the team.
     */
    @ManyToMany(mappedBy = "teams", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<User> users;

    /**
     * Default constructor that initializes the team's hash to a
     * random UUID, the creation date to the current time, and sets
     * up an empty user list.
     */
    public Team() {
        this.hash = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.users = new ArrayList<>();
    }

    /**
     * Adds a user to the team's user list.
     *
     * @param user The {@link User} to be added to the team.
     */
    public void addUser(final User user) {
        this.users.add(user);
    }

}
