package com.example.core.data

import app.cash.turbine.test
import com.core.data.repository.SportsRepositoryImpl
import com.core.domain.error.SportsError
import com.core.domain.repository.SportsRepository
import com.core.model.League
import com.core.model.Team
import com.core.model.TeamDetails
import com.core.network.service.SportsApiClient
import com.core.network.responses.LeaguesResponse
import com.core.network.responses.TeamDetailsResponse
import com.core.network.responses.TeamsResponse
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class SportsRepositoryTest {
    private lateinit var fakeApiService: FakeSportsApiService
    private lateinit var sportsApiClient: SportsApiClient
    private lateinit var repository: SportsRepository

    @Before
    fun setup() {
        fakeApiService = FakeSportsApiService()
        sportsApiClient = SportsApiClient(fakeApiService)
        repository = SportsRepositoryImpl(sportsApiClient)
    }

    @Test
    fun `getLeagues is successful when client returns data`() = runTest {
        val leagues = listOf(
            League(idLeague = "1", strLeague = "League 1"),
            League(idLeague = "2", strLeague = "League 2"),
        )

        fakeApiService.leaguesBehavior = Behavior.Success(LeaguesResponse(leagues = leagues))

        repository.getLeagues().test {
            val result = awaitItem()
            assertEquals(leagues, result)
            awaitComplete()
        }
    }

    @Test
    fun `getLeagues throws EmptyResult error when client returns empty list`() = runTest {
        val leagues = emptyList<League>()

        fakeApiService.leaguesBehavior =
            Behavior.Success(LeaguesResponse(leagues = leagues))

        repository.getLeagues().test {
            val error = awaitError()
            assertEquals(SportsError.EmptyResult, error)
        }
    }

    @Test
    fun `getLeagues throws error when client throws`() = runTest {
        val error = IOException("Network error")

        fakeApiService.leaguesBehavior =
            Behavior.Error(error)

        repository.getLeagues().test {
            val error = awaitError()
            assertEquals(SportsError.Network, error)
        }
    }

    @Test
    fun `getTeams emits success when client returns data`() = runTest {
        val teams = listOf(
            Team(idTeam = "1", strTeam = "Team 1"),
            Team(idTeam = "2", strTeam = "Team 2"),
        )

        fakeApiService.teamsBehavior = Behavior.Success(TeamsResponse(teams = teams))

        repository.getTeams("").test {
            val result = awaitItem()
            assertEquals(teams, result)
            awaitComplete()
        }
    }

    @Test
    fun `getTeams throws EmptyResult when client returns empty list`() = runTest {
        val teams = emptyList<Team>()

        fakeApiService.teamsBehavior = Behavior.Success(TeamsResponse(teams = teams))

        repository.getTeams("").test {
            val error = awaitError()
            assertEquals(SportsError.EmptyResult, error)
        }
    }

    @Test
    fun `getTeams throws error when client throws`() = runTest {
        val error = IOException("Network error")

        fakeApiService.teamsBehavior = Behavior.Error(error)

        repository.getTeams("").test {
            val error = awaitError()
            assertEquals(SportsError.Network, error)
        }
    }

    @Test
    fun `getTeamDetails emits success when client returns data`() = runTest {
        val teams = listOf(
            TeamDetails(
                idTeam = "1", strTeam = "Team 1",
                strLocation = null,
                strStadium = null,
                strDescription = null,
                badgeUrl = null
            ),
        )

        fakeApiService.teamDetailsBehavior = Behavior.Success(TeamDetailsResponse(teams = teams))

        repository.getTeamDetails("").test {
            val result = awaitItem()
            assertEquals(teams.first(), result)
            awaitComplete()
        }
    }

    @Test
    fun `getTeamDetails throws EmptyResult when client return empty list`() = runTest {
        val teamDetails = emptyList<TeamDetails>()

        fakeApiService.teamDetailsBehavior = Behavior.Success(TeamDetailsResponse(teamDetails))

        repository.getTeamDetails("").test {
            val result = awaitError()
            assertEquals(SportsError.EmptyResult, result)
        }
    }

    @Test
    fun `getTeamDetails throws when service throws`() = runTest {
        val error = IOException("Network error")

        fakeApiService.teamDetailsBehavior = Behavior.Error(error)

        repository.getTeamDetails("").test {
            val result = awaitError()
            assertEquals(SportsError.Network, result)
        }
    }
}

sealed class Behavior<T> {
    data class Success<T>(val value: T) : Behavior<T>()
    data class Error<T>(val throwable: Throwable) : Behavior<T>()

    fun execute(): T = when (this) {
        is Success -> value
        is Error -> throw throwable
    }
}
