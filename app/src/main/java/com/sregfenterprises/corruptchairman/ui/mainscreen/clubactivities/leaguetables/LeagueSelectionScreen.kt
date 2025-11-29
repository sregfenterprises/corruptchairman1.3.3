package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.data.ClubRepository
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LeagueSelectionScreen(
    clubRepository: ClubRepository,
    onLeagueSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    var leagues by remember { mutableStateOf<List<String>>(emptyList()) }

    // Load leagues from all clubs in the database
    LaunchedEffect(Unit) {
        clubRepository.getAllClubs().collectLatest { clubs ->
            leagues = clubs.map { it.league }.distinct()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Select a League",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        leagues.forEach { league ->
            Button(
                onClick = { onLeagueSelected(league) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(league)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
