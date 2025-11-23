package com.sregfenterprises.corruptchairman.ui.clubdetail

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.model.Club

@Composable
fun ClubDetailScreen(
    club: Club,
    showTakeoverButton: Boolean = false,        // NEW: toggle for takeover
    onTakeoverConfirmed: (() -> Unit)? = null,  // NEW: callback when button clicked
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = club.name, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Text("League: ${club.league}")
        Text("Country: ${club.country}")
        Text("Continent: ${club.continent}")
        Text("City: ${club.city}")
        Text("Population: ${club.population}")
        Text("Kit Color: ${club.kitColor}")

        if (showTakeoverButton && onTakeoverConfirmed != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    // Save club info in SharedPreferences
                    val prefs = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putBoolean("hasTakenOverClub", true)
                        putString("clubName", club.name)
                        putString("league", club.league)
                        putString("continent", club.continent)
                        putString("country", club.country)
                        putInt("population", club.population)
                        putString("city", club.city)
                        putString("kitColor", club.kitColor)
                        apply()
                    }

                    // Call callback to navigate to MainScreen
                    onTakeoverConfirmed()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Take Over Club")
            }
        }
    }
}
