package com.core.domain.usecase

import com.core.domain.repository.SportsRepository
import com.core.model.TeamDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetTeamDetailsUseCase {
    operator fun invoke(teamQuery: String): Flow<TeamDetails>
}

class GetTeamDetailsUseCaseImpl @Inject constructor(
    private val sportsRepository: SportsRepository
) : GetTeamDetailsUseCase {
    override operator fun invoke(teamQuery: String): Flow<TeamDetails> {
        return sportsRepository.getTeamDetails(teamQuery)
    }
}