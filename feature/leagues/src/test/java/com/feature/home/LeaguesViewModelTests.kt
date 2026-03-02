package com.feature.home

import app.cash.turbine.test
import com.core.model.League
import com.core.navigation.Destination
import com.core.navigation.NavigationAction
import com.feature.leagues.LeaguesUiState
import com.feature.leagues.LeaguesViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LeaguesViewModelTests {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getLeaguesUseCase: FakeGetLeaguesUseCase
    private lateinit var leaguesViewModel: LeaguesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState starts as Loading`() = runTest {
        getLeaguesUseCase = FakeGetLeaguesUseCase(leagues = emptyList())
        leaguesViewModel = LeaguesViewModel(getLeaguesUseCase)

        leaguesViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(LeaguesUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Success when leagues load successfully`() = runTest {
        val leagues = listOf(
            League("1", "Team 1"),
            League("2", "Team 2")
        )
        getLeaguesUseCase = FakeGetLeaguesUseCase(leagues = leagues)
        leaguesViewModel = LeaguesViewModel(getLeaguesUseCase)

        leaguesViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(LeaguesUiState.Loading)

            assertThat(awaitItem()).isEqualTo(LeaguesUiState.Success(leagues))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Failure when leagues load fails`() = runTest {
        val error = Throwable("A error occurred")

        getLeaguesUseCase = FakeGetLeaguesUseCase(error = error)
        leaguesViewModel = LeaguesViewModel(getLeaguesUseCase)

        leaguesViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(LeaguesUiState.Loading)

            assertThat(awaitItem()).isInstanceOf(LeaguesUiState.Failure::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits NavigateTo Teams when league clicked`() = runTest {
        getLeaguesUseCase = FakeGetLeaguesUseCase(leagues = emptyList())
        leaguesViewModel = LeaguesViewModel(getLeaguesUseCase)

        val league = "league"

        leaguesViewModel.navigation.test {
            leaguesViewModel.onLeagueClicked(league)

            assertThat(awaitItem())
                .isEqualTo(
                    NavigationAction.NavigateTo(
                        Destination.Teams(league)
                    )
                )
            cancelAndIgnoreRemainingEvents()
        }
    }
}