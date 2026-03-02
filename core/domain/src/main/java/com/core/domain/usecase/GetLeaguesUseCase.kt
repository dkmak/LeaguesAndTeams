package com.core.domain.usecase

import com.core.domain.repository.SportsRepository
import com.core.model.League
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetLeaguesUseCase {
    operator fun invoke(): Flow<List<League>>
}

class GetLeaguesUseCaseImpl @Inject constructor(
    private val sportsRepository: SportsRepository
): GetLeaguesUseCase {
    override operator fun invoke(): Flow<List<League>>{
        return sportsRepository.getLeagues()
    }
}