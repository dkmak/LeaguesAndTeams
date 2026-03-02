package com.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationAction {

    data class NavigateTo(
        val destination: Destination
    ): NavigationAction


    data object Back: NavigationAction
}