package com.feature.teams

import com.core.domain.usecase.GetTeamsUseCase
import com.core.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetTeamsUseCase(
    private val teams: List<Team>? = null,
    private val error: Throwable? = null
) : GetTeamsUseCase {
    override fun invoke(league: String): Flow<List<Team>> = flow {
        error?.let { throw it }
        emit(teams ?: emptyList())
    }
}