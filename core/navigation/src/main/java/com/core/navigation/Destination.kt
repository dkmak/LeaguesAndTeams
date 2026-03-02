package com.core.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Leagues: Destination

    @Serializable
    data class Teams(val league: String): Destination

    @Serializable
    data class TeamDetails(val team: String): Destination
}
