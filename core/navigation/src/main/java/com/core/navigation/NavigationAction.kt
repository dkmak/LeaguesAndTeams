package com.core.navigation
sealed interface NavigationAction {

    data class NavigateTo(
        val destination: Destination
    ): NavigationAction

    data object Back: NavigationAction
}