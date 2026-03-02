package com.core.network.responses

import com.core.model.TeamDetails
import kotlinx.serialization.Serializable

@Serializable
data class TeamDetailsResponse (
    val teams: List<TeamDetails>?
)
