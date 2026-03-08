package com.core.data.repository

import com.core.data.error.toSportsError
import com.core.data.mapper.toDomain
import com.core.domain.error.SportsError
import com.core.domain.repository.SportsRepository
import com.core.model.League
import com.core.model.Team
import com.core.model.TeamDetails
import com.core.network.service.SportsApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SportsRepositoryImpl @Inject constructor(
    private val sportsApiClient: SportsApiClient
) : SportsRepository {
    override fun getLeagues(): Flow<List<League>> = flow {
        val response = sportsApiClient.getLeaguesList()
        val leagues = response.leagues?.map { it.toDomain() } ?: throw SportsError.EmptyResult
        if (leagues.isEmpty()) {
            throw SportsError.EmptyResult
        }

        emit(leagues)
    }.catch { throwable ->
        throw throwable.toSportsError()
    }

    override fun getTeams(league: String): Flow<List<Team>> = flow {
        val response = sportsApiClient.getTeamsList(league)
        val teams = response.teams?.map { it.toDomain() } ?: throw SportsError.EmptyResult
        if (teams.isEmpty()) {
            throw SportsError.EmptyResult
        }

        emit(teams)
    }.catch { throwable ->
        throw throwable.toSportsError()
    }

    override fun getTeamDetails(teamQuery: String): Flow<TeamDetails>  = flow {
        val response = sportsApiClient.getTeamDetails(teamQuery)
        val teams = response.teams?.map { it.toDomain() } ?: throw SportsError.EmptyResult
        if (teams.isEmpty()) {
            throw SportsError.EmptyResult
        }
        emit(teams.first())
    }.catch { throwable ->
        throw throwable.toSportsError()
    }
}
