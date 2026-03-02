package com.feature.teams

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.core.model.Team
import com.core.navigation.Destination
import com.core.navigation.NavigationAction
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
class TeamsViewModelTests {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getTeamsUseCase: FakeGetTeamsUseCase
    private lateinit var teamsViewModel: TeamsViewModel
    val savedStateHandle = SavedStateHandle(mapOf("league" to "league"))

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
        getTeamsUseCase = FakeGetTeamsUseCase(teams = emptyList())
        teamsViewModel = TeamsViewModel(
            getTeamsUseCase,
            savedStateHandle
        )

        teamsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamsUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Success when teams load successfully`() = runTest {
        val teams = listOf(
            Team("1", "Team 1"),
            Team("2", "Team 2")
        )

        getTeamsUseCase = FakeGetTeamsUseCase(teams = teams)
        teamsViewModel = TeamsViewModel(
            getTeamsUseCase,
            savedStateHandle
        )

        teamsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamsUiState.Loading)

            assertThat(awaitItem()).isEqualTo(TeamsUiState.Success(teams))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Failure when teams load fails`() = runTest {
        val error = Throwable("A error occurred")

        getTeamsUseCase = FakeGetTeamsUseCase(error = error)
        teamsViewModel = TeamsViewModel(
            getTeamsUseCase,
            savedStateHandle
        )

        teamsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamsUiState.Loading)

            assertThat(awaitItem()).isInstanceOf(TeamsUiState.Failure::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits NavigateTo TeamDetails when team clicked`() = runTest {
        getTeamsUseCase = FakeGetTeamsUseCase(teams = emptyList())
        teamsViewModel = TeamsViewModel(
            getTeamsUseCase,
            savedStateHandle
        )

        val team = "team"

        teamsViewModel.navigation.test {
            teamsViewModel.onTeamClicked(team)

            assertThat(awaitItem())
                .isEqualTo(
                    NavigationAction.NavigateTo(
                        Destination.TeamDetails(team)
                    )
                )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Back when back clicked`() = runTest {
        getTeamsUseCase = FakeGetTeamsUseCase(teams = emptyList())
        teamsViewModel = TeamsViewModel(
            getTeamsUseCase,
            savedStateHandle
        )

        teamsViewModel.navigation.test {
            teamsViewModel.onBackClicked()

            assertThat(awaitItem())
                .isEqualTo(
                    NavigationAction.Back
                )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
