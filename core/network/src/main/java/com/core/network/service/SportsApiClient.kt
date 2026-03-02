package com.core.network.service

import com.core.network.responses.LeaguesResponse
import com.core.network.responses.TeamDetailsResponse
import com.core.network.responses.TeamsResponse
import javax.inject.Inject

class SportsApiClient @Inject constructor(
    val sportsApiService: SportsApiService
){
    suspend fun getLeaguesList(): LeaguesResponse {
        return sportsApiService.fetchLeagues()
    }

    suspend fun getTeamsList(league: String): TeamsResponse {
        return sportsApiService.fetchTeams(league)
    }

    suspend fun getTeamDetails(teamQuery: String): TeamDetailsResponse {
        return sportsApiService.fetchTeamDetails(teamQuery)
    }
}