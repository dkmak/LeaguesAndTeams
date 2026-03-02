package com.feature.home

import com.core.domain.usecase.GetLeaguesUseCase
import com.core.model.League
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetLeaguesUseCase(
    private val leagues: List<League>? = null,
    private val error: Throwable? = null
) : GetLeaguesUseCase {
    override fun invoke(): Flow<List<League>> = flow {
        error?.let { throw it }
        emit(leagues ?: emptyList())
    }
}