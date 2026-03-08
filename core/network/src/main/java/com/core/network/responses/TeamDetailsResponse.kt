package com.core.network.responses

import com.core.network.dto.TeamDetailsDto
import kotlinx.serialization.Serializable

@Serializable
data class TeamDetailsResponse (
    val teams: List<TeamDetailsDto>?
)
