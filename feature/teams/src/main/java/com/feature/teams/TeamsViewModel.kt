package com.feature.teams

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.ui.toUserMessage
import com.core.domain.usecase.GetTeamsUseCase
import com.core.model.Team
import com.core.navigation.Destination
import com.core.navigation.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface TeamsUiState {
    data class Success(val teams: List<Team>) : TeamsUiState
    data class Failure(val message: String) : TeamsUiState
    data object Loading : TeamsUiState
}

@HiltViewModel
class TeamsViewModel @Inject constructor(
    getTeamsUseCase: GetTeamsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _navigation = MutableSharedFlow<NavigationAction>()
    val navigation = _navigation.asSharedFlow()

    private val _filterQuery = MutableStateFlow("")
    val filterQuery = _filterQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TeamsUiState> =
        savedStateHandle.getStateFlow("league", initialValue = "")
            .flatMapLatest { league ->
                getTeamsUseCase(league).combine(filterQuery) { list, filter ->
                    val filterList = if (filter.isEmpty()) {
                        list
                    } else {
                        list.filter { team -> team.strTeam.contains(filter, ignoreCase = true)}
                    }
                    filterList
                }.map<List<Team>, TeamsUiState> { teams ->
                    TeamsUiState.Success(teams)
                }
                    .catch { throwable ->
                        emit(
                            TeamsUiState.Failure(
                                throwable.toUserMessage()
                            )
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TeamsUiState.Loading
            )

    fun onTeamClicked(team: String) {
        viewModelScope.launch {
            _navigation.emit(NavigationAction.NavigateTo(Destination.TeamDetails(team)))
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _navigation.emit(NavigationAction.Back)
        }
    }

}