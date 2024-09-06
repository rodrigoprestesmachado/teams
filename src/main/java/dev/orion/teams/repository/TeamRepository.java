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

package dev.orion.teams.repository;

import dev.orion.teams.model.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing {@link Team} entities.
 *
 * This class provides database access methods for {@link Team} entities,
 * leveraging the PanacheRepository interface from Quarkus for standard
 * CRUD operations.
 */
@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {

	/**
	 * Finds a {@link Team} entity based on the given hash.
	 *
	 * @param hash The unique hash identifier for the team.
	 * @return The {@link Team} entity that matches the hash, or null if no
	 *         matching team is found.
	 */
	public Team findByHash(final String hash) {
		return find("hash", hash).firstResult();
	}

}
