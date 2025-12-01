package com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun clubactivitiesscreen(
    onBack: () -> Unit,
    onProfileClicked: () -> Unit,
    onBusinessActivities: () -> Unit = {},
    onSquad: () -> Unit = {},
    onBackroomStaff: () -> Unit = {},
    onStadium: () -> Unit = {},
    onLeagueTables: () -> Unit,
    onFixtures: () -> Unit   // ✅ New parameter
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Club Activities",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onSquad,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Squad") }

        Button(
            onClick = onBackroomStaff,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Backroom Staff") }

        Button(
            onClick = onFixtures,   // ✅ Hooked up
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Fixtures") }

        Button(
            onClick = onLeagueTables,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("League Tables") }

        Button(
            onClick = { /* TODO: Training */ },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Training") }

        Button(
            onClick = onStadium,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Stadium") }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Back")
        }
    }
}
