package com.core.network.responses

import com.core.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponse (
    val teams: List<Team>?
)