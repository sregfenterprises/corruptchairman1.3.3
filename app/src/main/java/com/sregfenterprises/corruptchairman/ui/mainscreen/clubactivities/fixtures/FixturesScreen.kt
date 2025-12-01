package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.fixtures

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.viewmodel.FixturesViewModel

@Composable
fun FixturesScreen(
    repository: ClubRepository,
    onBack: () -> Unit
) {

    val fixturesViewModel: FixturesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FixturesViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return FixturesViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )

    val fixtures by fixturesViewModel.fixtures.collectAsState()

    LaunchedEffect(Unit) { fixturesViewModel.loadFixtures() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }

        Spacer(modifier = Modifier.height(16.dp))

        if (fixtures.isEmpty()) {
            Text("No fixtures scheduled.", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn {
                itemsIndexed(fixtures) { _, match ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${match.homeClub.name} vs ${match.awayClub.name}")
                        Text("${match.homeGoals} - ${match.awayGoals}")
                    }
                }
            }
        }
    }
}
