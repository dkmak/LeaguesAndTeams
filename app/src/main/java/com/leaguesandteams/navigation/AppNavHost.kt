package com.leaguesandteams.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.core.navigation.Destination.Teams
import com.core.navigation.Destination.Leagues
import com.core.navigation.Destination.TeamDetails
import com.core.navigation.NavigationAction
import com.feature.leagues.LeaguesScreen
import com.feature.teamdetails.TeamDetailsScreen
import com.feature.teams.TeamsScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Leagues
    ) {
        fun NavigationAction.processNavigationAction() = when (this) {
            NavigationAction.Back -> {
                navHostController.popBackStack()
            }

            is NavigationAction.NavigateTo -> {
                navHostController.navigate(this.destination)
            }
        }
        composable<Leagues> { navBackStackEntry ->
            LeaguesScreen(
                onNavigate = { navAction ->
                    navAction.processNavigationAction()
                }
            )
        }
        composable<Teams> { navBackStackEntry ->
            TeamsScreen(
                onNavigate = { navAction ->
                    navAction.processNavigationAction()
                }
            )
        }
        composable<TeamDetails> { navBackStackEntry ->
            TeamDetailsScreen(
                onNavigate = { navAction ->
                    navAction.processNavigationAction()
                }
            )
        }
    }
}