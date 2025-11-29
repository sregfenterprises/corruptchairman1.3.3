package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.viewmodel.LeagueViewModel

@Composable
fun LeagueSelectionScreen(
    leagueViewModel: LeagueViewModel,
    onLeagueSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val leagues = leagueViewModel.leagues.value

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
                onClick = { onLeagueSelected(league.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(league.name)
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
