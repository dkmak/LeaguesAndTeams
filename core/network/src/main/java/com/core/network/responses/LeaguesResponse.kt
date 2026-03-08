package com.core.network.responses

import com.core.network.dto.LeagueDto
import kotlinx.serialization.Serializable

@Serializable
data class LeaguesResponse (
    val leagues: List<LeagueDto>?
)
