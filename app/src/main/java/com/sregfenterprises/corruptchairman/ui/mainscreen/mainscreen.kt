package com.sregfenterprises.corruptchairman.ui.mainscreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.model.Club

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onChairmanProfileClicked: () -> Unit // Callback to navigate to ChairmanProfileScreen
) {
    val context = LocalContext.current

    // Load club from SharedPreferences
    var club by remember { mutableStateOf<Club?>(null) }

    LaunchedEffect(Unit) {
        club = loadClubFromPrefs(context)
    }

    club?.let { currentClub ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = currentClub.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(currentClub.kitColor)))
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Core main screen buttons
            Button(
                onClick = { /* TODO: navigate to Business Activities */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) { Text("Business Activities") }

            Button(
                onClick = { /* TODO: navigate to Squad */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) { Text("Squad") }

            Button(
                onClick = { /* TODO: navigate to Backroom Staff */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) { Text("Backroom Staff") }

            Button(
                onClick = { /* TODO: navigate to Stadium */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) { Text("Stadium") }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Chairman Profile button
            Button(
                onClick = onChairmanProfileClicked,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Chairman Profile")
            }
        }
    } ?: run {
        // No club found placeholder
        Text("No club selected", modifier = Modifier.padding(24.dp))
    }
}

private fun loadClubFromPrefs(context: Context): Club? {
    val prefs = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
    val hasClub = prefs.getBoolean("hasTakenOverClub", false)
    return if (!hasClub) null
    else Club(
        name = prefs.getString("clubName", "") ?: "",
        league = prefs.getString("league", "") ?: "",
        continent = prefs.getString("continent", "") ?: "",
        country = prefs.getString("country", "") ?: "",
        population = prefs.getInt("population", 0),
        city = prefs.getString("city", "") ?: "",
        kitColor = prefs.getString("kitColor", "#FF0000") ?: "#FF0000"
    )
}
