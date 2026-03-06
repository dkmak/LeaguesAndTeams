package com.feature.teamdetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.core.common.ui.compose.LeaguesAndTeamsAppBar
import com.core.common.ui.theme.LeaguesAndTeamsTheme
import com.core.model.TeamDetails
import com.core.navigation.NavigationAction

@Composable
fun TeamDetailsScreen(
    teamDetailsViewModel: TeamDetailsViewModel = hiltViewModel(),
    onNavigate: (NavigationAction) -> Unit
) {
    val uiState by teamDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(teamDetailsViewModel.navigation) {
        teamDetailsViewModel.navigation.collect { navAction ->
            onNavigate(navAction)
        }
    }

    TeamDetailsScreen(
      uiState = uiState,
      scrollState = scrollState,
      onBackClicked = {teamDetailsViewModel.onBackClicked()}
    )
}

@Composable
fun TeamDetailsScreen(
    uiState: TeamDetailsUiState,
    scrollState: ScrollState,
    onBackClicked: () -> Unit
){
    Scaffold(
        topBar = {
            LeaguesAndTeamsAppBar(
                title = "Team Details",
                showBackButton = true,
                onBackClicked = onBackClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is TeamDetailsUiState.Failure -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = state.message)
                    }
                }

                TeamDetailsUiState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TeamDetailsUiState.Success -> {
                    DisplayTeamDetails(
                        teamDetails = state.teamDetails,
                        scrollState = scrollState,
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
fun DisplayTeamDetails(
    teamDetails: TeamDetails,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        teamDetails.badgeUrl?.let {
            AsyncImage(
                model = teamDetails.badgeUrl,
                contentDescription = teamDetails.strTeam,
                modifier = Modifier.size(360.dp)
            )
        }
        Text(
            text = teamDetails.strTeam,
            modifier = Modifier.padding(vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        teamDetails.strLocation?.let { location ->
            Text(
                text = location,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        teamDetails.strStadium?.let { stadium ->
            Text(
                text = stadium,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        teamDetails.strDescription?.let { description ->
            Text(
                text = description,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDetailsScreenSuccessPreview() {
    LeaguesAndTeamsTheme {
        TeamDetailsScreen(
            uiState = TeamDetailsUiState.Success(
                teamDetails = TeamDetails(
                    idTeam = "133604",
                    strTeam = "Arsenal",
                    strLocation = "London, England",
                    strStadium = "Emirates Stadium",
                    strDescription = "Arsenal Football Club is a professional football club based in London.",
                    badgeUrl = null
                )
            ),
            scrollState = rememberScrollState(),
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDetailsScreenFailurePreview() {
    LeaguesAndTeamsTheme {
        TeamDetailsScreen(
            uiState = TeamDetailsUiState.Failure(
                message = "An Unknown Error Occurred"
            ),
            scrollState = rememberScrollState(),
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDetailsScreenLoadingPreview() {
    LeaguesAndTeamsTheme {
        TeamDetailsScreen(
            uiState = TeamDetailsUiState.Loading,
            scrollState = rememberScrollState(),
            onBackClicked = {}
        )
    }
}
