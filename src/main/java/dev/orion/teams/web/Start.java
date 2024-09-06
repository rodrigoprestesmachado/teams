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

package dev.orion.teams.web;

import dev.orion.teams.model.Team;
import dev.orion.teams.model.User;
import dev.orion.teams.repository.TeamRepository;
import dev.orion.teams.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST API resource class that provides endpoints for team management.
 * This class handles operations such as creating a new team, finding
 * an existing team by its hash, and adding a user to a team.
 */
@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class Start {

    /** Repository for team-related operations. */
    @Inject
    private TeamRepository teamRepository;

    /** Repository for user-related operations. */
    @Inject
    private UserRepository userRepository;

    /**
     * Creates a new team and persists it to the database.
     *
     * @return The created Team object.
     */
    @POST
    @Path("/create")
    public Team create() {
        Team team = new Team();
        teamRepository.persist(team);
        return team;
    }

    /**
     * Finds a team by its hash identifier.
     *
     * @param hashTeam The hash identifier of the team.
     * @return The Team object if found, otherwise null.
     */
    @GET
    @Path("/find/{hashTeam}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Team findTeam(@PathParam("hashTeam") final String hashTeam) {
        return teamRepository.findByHash(hashTeam);
    }

    /**
     * Adds a user to a team by their respective hash identifiers.
     * If the user does not exist, a new user is created and added
     * to the team.
     *
     * @param hashTeam The hash identifier of the team.
     * @param hashUser The hash identifier of the user.
     * @return The updated Team object.
     */
    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Team join(
            @FormParam("hashTeam") final String hashTeam,
            @FormParam("hashUser") final String hashUser) {

        Team team = teamRepository.findByHash(hashTeam);
        if (team != null) {
            User user = userRepository.findByHash(hashUser);
            if (user == null) {
                user = new User();
            }
            user.setHash(hashUser);
            team.addUser(user);
            user.addTeam(team);
            teamRepository.persist(team);
        }
        return team;
    }
}
