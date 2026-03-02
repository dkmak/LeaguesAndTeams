package com.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Team (
    val idTeam: String,
    val strTeam: String
)