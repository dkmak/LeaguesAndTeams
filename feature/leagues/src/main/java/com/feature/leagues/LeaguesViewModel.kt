package com.feature.leagues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.ui.toUserMessage
import com.core.domain.error.SportsError
import com.core.domain.usecase.GetLeaguesUseCase
import com.core.model.League
import com.core.navigation.Destination
import com.core.navigation.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LeaguesUiState {
    data class Success(val leagues: List<League>) : LeaguesUiState
    data class Failure(val message: String) : LeaguesUiState
    data object Loading : LeaguesUiState
}

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    getLeaguesUseCase: GetLeaguesUseCase
) : ViewModel() {
    private val _navigation = MutableSharedFlow<NavigationAction>()
    val navigation = _navigation.asSharedFlow()

    val uiState: StateFlow<LeaguesUiState> = getLeaguesUseCase()
        .map<List<League>, LeaguesUiState> { leagues -> LeaguesUiState.Success(leagues) }
        .catch { throwable ->
            emit(
                LeaguesUiState.Failure(
                    throwable.toUserMessage()
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LeaguesUiState.Loading
        )

    fun onLeagueClicked(league: String) {
        viewModelScope.launch {
            _navigation.emit(NavigationAction.NavigateTo(Destination.Teams(league)))
        }
    }
}