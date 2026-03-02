package com.core.domain.repository

import com.core.model.League
import com.core.model.Team
import com.core.model.TeamDetails
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    fun getLeagues(): Flow<List<League>>
    fun getTeams(league: String): Flow<List<Team>>
    fun getTeamDetails(teamQuery: String): Flow<TeamDetails>
}