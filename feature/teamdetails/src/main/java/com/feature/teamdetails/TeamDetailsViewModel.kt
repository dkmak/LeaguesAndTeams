package com.feature.teamdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.ui.toUserMessage
import com.core.domain.error.SportsError
import com.core.domain.usecase.GetTeamDetailsUseCase
import com.core.model.TeamDetails
import com.core.navigation.Destination
import com.core.navigation.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TeamDetailsUiState {
    data class Success(val teamDetails: TeamDetails): TeamDetailsUiState
    data class Failure(val message: String): TeamDetailsUiState
    data object Loading: TeamDetailsUiState
}

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    getTeamDetailsUseCase: GetTeamDetailsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _navigation = MutableSharedFlow<NavigationAction>()
    val navigation = _navigation.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TeamDetailsUiState> = savedStateHandle.getStateFlow("team", initialValue = "")
        .flatMapLatest { team ->
            getTeamDetailsUseCase(team)
                .map<TeamDetails, TeamDetailsUiState>{ teamDetails ->
                    TeamDetailsUiState.Success(teamDetails)
                }
                .catch { throwable ->
                    emit(
                        TeamDetailsUiState.Failure(
                            throwable.toUserMessage()
                        )
                    )
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TeamDetailsUiState.Loading
        )

    fun onBackClicked(){
        viewModelScope.launch {
            _navigation.emit(NavigationAction.Back)
        }
    }
}


