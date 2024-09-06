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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user in the system.
 *
 * Each user is identified by a unique hash. This class extends
 * {@link PanacheEntity} to inherit standard database operations.
 * The {@link Entity} annotation indicates that this class is
 * a JPA entity, and the {@link Getter} and {@link Setter} annotations
 * are used to automatically generate getter and setter methods
 * for the fields.
 */
@Entity
@Getter
@Setter
public class User extends PanacheEntity {

    /**
     * The user's unique hash, used for identification.
     */
    private String hash;

    /**
     * The user's first name.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Team> teams;

    /**
     * Default constructor for the User class.
     */
    public User() {
        this.teams = new ArrayList<>();
    }

    /**
     * Adds a team to the user's list of teams.
     *
     * @param team The team to add.
     */
    public void addTeam(Team team) {
        this.teams.add(team);
    }

}
