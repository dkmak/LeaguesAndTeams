package com.feature.teams

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
import com.core.model.Team
import com.core.navigation.NavigationAction

@Composable
fun TeamsScreen(
    teamsViewModel: TeamsViewModel = hiltViewModel(),
    onNavigate: (NavigationAction) -> Unit
) {
    val uiState by teamsViewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(teamsViewModel.navigation) {
        teamsViewModel.navigation.collect { navAction ->
            onNavigate(navAction)
        }
    }

    TeamsScreen(
        uiState = uiState,
        listState = listState,
        onTeamClicked = { team -> teamsViewModel.onTeamClicked(team) },
        onBackClicked = {teamsViewModel.onBackClicked()}
    )

}

@Composable
fun TeamsScreen(
    uiState: TeamsUiState,
    listState: LazyListState,
    onTeamClicked: (String) -> Unit,
    onBackClicked: () -> Unit
){
    Scaffold(
        topBar = {
            LeaguesAndTeamsAppBar(
                title = "Teams",
                showBackButton = true,
                onBackClicked = onBackClicked
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
                is TeamsUiState.Failure -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = state.message)
                    }
                }

                TeamsUiState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TeamsUiState.Success -> {
                    DisplayTeamsList(
                        teams = state.teams,
                        listState = listState,
                        onTeamClicked = onTeamClicked,
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
fun DisplayTeamsList(
    teams: List<Team>,
    listState: LazyListState,
    onTeamClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(
            items = teams,
            key = { team -> team.idTeam }
        ) { team ->
            DisplayTeamItem(
                team = team,
                onTeamClicked = onTeamClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun DisplayTeamItem(
    team: Team,
    onTeamClicked: (String) -> Unit,
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
                .clickable { onTeamClicked(team.strTeam) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = team.strTeam,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenSuccessPreview() {
    LeaguesAndTeamsTheme {
        TeamsScreen(
            uiState = TeamsUiState.Success(
                teams = listOf(
                    Team(idTeam = "133604", strTeam = "Arsenal"),
                    Team(idTeam = "133602", strTeam = "Chelsea"),
                    Team(idTeam = "133610", strTeam = "Liverpool")
                )
            ),
            listState = rememberLazyListState(),
            onTeamClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenFailurePreview() {
    LeaguesAndTeamsTheme {
        TeamsScreen(
            uiState = TeamsUiState.Failure(
                message = "An Unknown Error Occurred"
            ),
            listState = rememberLazyListState(),
            onTeamClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenLoadingPreview() {
    LeaguesAndTeamsTheme {
        TeamsScreen(
            uiState = TeamsUiState.Loading,
            listState = rememberLazyListState(),
            onTeamClicked = {},
            onBackClicked = {}
        )
    }
}
