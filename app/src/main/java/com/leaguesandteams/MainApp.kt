package com.leaguesandteams

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.core.common.ui.theme.LeaguesAndTeamsTheme
import com.leaguesandteams.navigation.AppNavHost

@Composable
fun MainApp() {
    LeaguesAndTeamsTheme {
            val navController = rememberNavController()
            AppNavHost(navController)
    }
}