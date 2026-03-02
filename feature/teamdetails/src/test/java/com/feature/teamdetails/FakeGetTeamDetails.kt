package com.feature.teamdetails

import com.core.domain.usecase.GetTeamDetailsUseCase
import com.core.model.TeamDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetTeamDetailsUseCase(
    private val teamDetails: TeamDetails? = null,
    private val error: Throwable? = null
) : GetTeamDetailsUseCase {
    override fun invoke(teamQuery: String): Flow<TeamDetails> = flow {
        error?.let { throw it }
        emit(
            teamDetails ?: TeamDetails(
                idTeam = "1",
                strTeam = "Team Details 1",
                strLocation = null,
                strStadium = null,
                strDescription = null,
                badgeUrl = null
            )
        )
    }

}
