package com.core.network.service

import com.core.network.responses.LeaguesResponse
import com.core.network.responses.TeamDetailsResponse
import com.core.network.responses.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApiService {
    @GET("all_leagues.php")
    suspend fun fetchLeagues(): LeaguesResponse

    @GET("search_all_teams.php")
    suspend fun fetchTeams(
        @Query("l") league: String
    ): TeamsResponse

    @GET("searchteams.php")
    suspend fun fetchTeamDetails(
        @Query("t") teamQuery: String
    ): TeamDetailsResponse
}
