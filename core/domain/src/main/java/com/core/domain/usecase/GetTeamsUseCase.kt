package com.core.domain.usecase

import com.core.domain.repository.SportsRepository
import com.core.model.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetTeamsUseCase {
    operator fun invoke(league: String): Flow<List<Team>>
}

class GetTeamsUseCaseImpl @Inject constructor(
    private val sportsRepository: SportsRepository
) : GetTeamsUseCase {
    override operator fun invoke(league: String): Flow<List<Team>> {
        return sportsRepository.getTeams(league)
    }
}