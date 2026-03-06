package com.feature.leagues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.common.ui.compose.LeaguesAndTeamsAppBar
import com.core.common.ui.theme.LeaguesAndTeamsTheme
import com.core.model.League
import com.core.navigation.NavigationAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaguesScreen(
    leaguesViewModel: LeaguesViewModel = hiltViewModel(),
    onNavigate: (NavigationAction) -> Unit
) {
    val uiState by leaguesViewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(leaguesViewModel.navigation) { ->
        leaguesViewModel.navigation.collect { navAction ->
            onNavigate(navAction)
        }
    }

    LeaguesScreen(
        uiState = uiState,
        listState = listState,
        onLeagueClicked = {league -> leaguesViewModel.onLeagueClicked(league)}
    )
}

@Composable
fun LeaguesScreen(
    uiState: LeaguesUiState,
    listState: LazyListState,
    onLeagueClicked: (String)-> Unit
){
    Scaffold(
        topBar = {
            LeaguesAndTeamsAppBar(
                "Leagues",
                showBackButton = false
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is LeaguesUiState.Failure -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = state.message)
                    }
                }

                LeaguesUiState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LeaguesUiState.Success -> {
                    DisplayLeagueList(
                        leagues = state.leagues,
                        listState = listState,
                        onLeagueClicked = onLeagueClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun DisplayLeagueList(
    leagues: List<League>,
    listState: LazyListState,
    onLeagueClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(
            items = leagues,
            key = { league -> league.idLeague }
        ) { league ->
            DisplayLeagueItem(
                league,
                onLeagueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun DisplayLeagueItem(
    league: League,
    onLeagueClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onLeagueClicked(league.strLeague) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = league.strLeague,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayLeagueListPreview() {
    LeaguesAndTeamsTheme {
        DisplayLeagueList(
            leagues = listOf(
                League(idLeague = "4328", strLeague = "English Premier League"),
                League(idLeague = "4329", strLeague = "English Championship"),
                League(idLeague = "4330", strLeague = "Scottish Premiership")
            ),
            listState = rememberLazyListState(),
            onLeagueClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LeaguesScreenPreview() {
    LeaguesAndTeamsTheme {
        LeaguesScreen(
            uiState = LeaguesUiState.Success(
                leagues = listOf(
                    League(idLeague = "4328", strLeague = "English Premier League"),
                    League(idLeague = "4329", strLeague = "English Championship"),
                    League(idLeague = "4330", strLeague = "Scottish Premiership")
                )
            ),
            listState = rememberLazyListState(),
            onLeagueClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LeaguesScreenFailurePreview() {
    LeaguesAndTeamsTheme {
        LeaguesScreen(
            uiState = LeaguesUiState.Failure(
                message = "An Unknown Error Occurred"
            ),
            listState = rememberLazyListState(),
            onLeagueClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LeaguesScreenLoadingPreview() {
    LeaguesAndTeamsTheme {
        LeaguesScreen(
            uiState = LeaguesUiState.Loading,
            listState = rememberLazyListState(),
            onLeagueClicked = {}
        )
    }
}