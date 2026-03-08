package com.core.network.responses

import com.core.network.dto.TeamDto
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponse (
    val teams: List<TeamDto>?
)
