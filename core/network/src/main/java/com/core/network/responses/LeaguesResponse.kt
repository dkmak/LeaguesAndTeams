package com.core.network.responses

import com.core.model.League
import kotlinx.serialization.Serializable

@Serializable
data class LeaguesResponse (
    val leagues: List<League>?
)

