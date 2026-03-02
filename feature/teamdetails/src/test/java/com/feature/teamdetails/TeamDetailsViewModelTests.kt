package com.feature.teamdetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.core.model.TeamDetails
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
class TeamDetailsViewModelTests {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getTeamDetailsUseCase: FakeGetTeamDetailsUseCase
    private lateinit var teamDetailsViewModel: TeamDetailsViewModel
    val savedStateHandle = SavedStateHandle(mapOf("team" to "team"))

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
        getTeamDetailsUseCase = FakeGetTeamDetailsUseCase()
        teamDetailsViewModel = TeamDetailsViewModel(
            getTeamDetailsUseCase,
            savedStateHandle
        )

        teamDetailsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamDetailsUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Success when team details load successfully`() = runTest {
        val teamDetails = TeamDetails(
            idTeam = "1",
            strTeam = "team",
            strLocation = null,
            strStadium = null,
            strDescription = null,
            badgeUrl = null
        )

        getTeamDetailsUseCase = FakeGetTeamDetailsUseCase(teamDetails = teamDetails)
        teamDetailsViewModel = TeamDetailsViewModel(
            getTeamDetailsUseCase,
            savedStateHandle
        )

        teamDetailsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamDetailsUiState.Loading)

            assertThat(awaitItem()).isEqualTo(TeamDetailsUiState.Success(teamDetails))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit Failure when team details load fails`() = runTest {
        val error = Throwable("A error occurred")

        getTeamDetailsUseCase = FakeGetTeamDetailsUseCase(error = error)
        teamDetailsViewModel = TeamDetailsViewModel(
            getTeamDetailsUseCase,
            savedStateHandle
        )

        teamDetailsViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(TeamDetailsUiState.Loading)

            assertThat(awaitItem()).isInstanceOf(TeamDetailsUiState.Failure::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Back when back clicked`() = runTest {
        getTeamDetailsUseCase = FakeGetTeamDetailsUseCase()
        teamDetailsViewModel = TeamDetailsViewModel(
            getTeamDetailsUseCase,
            savedStateHandle
        )

        teamDetailsViewModel.navigation.test {
            teamDetailsViewModel.onBackClicked()

            assertThat(awaitItem())
                .isEqualTo(
                    NavigationAction.Back
                )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
