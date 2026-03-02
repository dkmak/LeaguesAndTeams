package com.example.core.data

import com.core.network.service.SportsApiService
import com.core.network.responses.LeaguesResponse
import com.core.network.responses.TeamDetailsResponse
import com.core.network.responses.TeamsResponse

class FakeSportsApiService(
    var leaguesBehavior: Behavior<LeaguesResponse> = Behavior.Success(
        LeaguesResponse(emptyList())
    ),
    var teamsBehavior: Behavior<TeamsResponse> = Behavior.Success(
        TeamsResponse(emptyList())
    ),
    var teamDetailsBehavior: Behavior<TeamDetailsResponse> = Behavior.Success(
        TeamDetailsResponse(emptyList())
    ),
) : SportsApiService {

    override suspend fun fetchLeagues(): LeaguesResponse =
        leaguesBehavior.execute()

    override suspend fun fetchTeams(league: String): TeamsResponse {
        return teamsBehavior.execute()
    }

    override suspend fun fetchTeamDetails(teamQuery: String): TeamDetailsResponse {
        return teamDetailsBehavior.execute()
    }
}