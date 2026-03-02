package com.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDetails(
    val idTeam: String,
    val strTeam: String,
    val strLocation: String?,
    val strStadium: String?,
    @SerialName("strDescriptionEN") val strDescription: String?,
    @SerialName("strBadge") val badgeUrl: String?
)