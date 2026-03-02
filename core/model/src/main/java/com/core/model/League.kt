package com.core.model

import kotlinx.serialization.Serializable

@Serializable
data class League (
    val idLeague: String,
    val strLeague: String
)