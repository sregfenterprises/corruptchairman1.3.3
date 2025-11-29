package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.viewmodel.LeagueViewModel

@Composable
fun LeagueTableScreen(
    leagueName: String,
    leagueViewModel: LeagueViewModel,
    onBack: () -> Unit
) {
    val leagues = leagueViewModel.leagues.value
    val league = leagues.firstOrNull { it.name == leagueName }

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

        if (league == null) {
            Text("League not found.")
            return@Column
        }

        Text(
            text = league.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        val sortedClubs = league.clubs.sortedByDescending { it.rankingPoints }

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
