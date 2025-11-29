package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.model.Club
import kotlinx.coroutines.flow.collect

@Composable
fun LeagueTableScreen(
    leagueName: String,
    clubRepository: ClubRepository,
    onBack: () -> Unit
) {
    var clubs by remember { mutableStateOf<List<Club>>(emptyList()) }

    // Collect the Flow from the repository
    LaunchedEffect(leagueName) {
        clubRepository.getClubsByLeague(leagueName).collect { clubList ->
            clubs = clubList
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = leagueName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (clubs.isEmpty()) {
            Text("No clubs found for this league.")
        } else {
            val sortedClubs = clubs.sortedByDescending { it.rankingPoints }

            LazyColumn {
                itemsIndexed(sortedClubs) { index, club ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}.",
                            modifier = Modifier.width(32.dp)
                        )
                        Text(
                            text = club.name,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${club.rankingPoints} pts"
                        )
                    }
                }
            }
        }
    }
}
