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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sregfenterprises.corruptchairman.model.Club
import com.sregfenterprises.corruptchairman.viewmodel.LeagueViewModel
import com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables.LeagueSelectionScreen
import com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.leaguetables.LeagueTableScreen
import com.sregfenterprises.corruptchairman.ui.mainscreen.chairmanactivities.ChairmanActivitiesScreen
import com.sregfenterprises.corruptchairman.ui.mainscreen.chairmanactivities.ChairmanProfileScreen
import com.sregfenterprises.corruptchairman.ui.mainscreen.clubactivities.clubactivitiesscreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    leagueViewModel: LeagueViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainHome"
    ) {

        // MAIN HOME SCREEN
        composable("mainHome") {
            MainHomeContent(
                modifier = modifier,
                onChairmanActivities = { navController.navigate("chairmanActivities") },
                onClubActivities = { navController.navigate("clubActivities") }
            )
        }

        // CHAIRMAN ACTIVITIES SCREEN
        composable("chairmanActivities") {
            ChairmanActivitiesScreen(
                onBack = { navController.popBackStack() },
                onProfileClicked = { navController.navigate("chairmanProfile") }
            )
        }

        // CHAIRMAN PROFILE SCREEN
        composable("chairmanProfile") {
            ChairmanProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // CLUB ACTIVITIES SCREEN
        composable("clubActivities") {
            clubactivitiesscreen(
                onBack = { navController.popBackStack() },
                onProfileClicked = { /* TODO */ },
                onLeagueTables = { navController.navigate("league_selection") }  // NEW
            )
        }

        // ⭐ NEW ROUTE: SELECT LEAGUE (World or Europe)
        composable("league_selection") {
            LeagueSelectionScreen(
                onLeagueSelected = { leagueName ->
                    navController.navigate("league_table/$leagueName")
                },
                onBack = { navController.popBackStack() },
                leagueViewModel = leagueViewModel
            )
        }

        // ⭐ NEW ROUTE: DISPLAY LEAGUE TABLE
        composable("league_table/{leagueName}") { backStackEntry ->
            val leagueName = backStackEntry.arguments?.getString("leagueName") ?: ""
            LeagueTableScreen(
                leagueName = leagueName,
                leagueViewModel = leagueViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun MainHomeContent(
    modifier: Modifier = Modifier,
    onChairmanActivities: () -> Unit,
    onClubActivities: () -> Unit
) {
    val context = LocalContext.current
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentClub.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(android.graphics.Color.parseColor(currentClub.kitColor)))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            Button(
                onClick = onChairmanActivities,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Chairman Activities")
            }

            Button(
                onClick = onClubActivities,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Club Activities")
            }
        }
    } ?: run {
        Text("No club selected", modifier = Modifier.padding(24.dp))
    }
}

private fun loadClubFromPrefs(context: Context): Club? {
    val prefs = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)

    return if (!prefs.getBoolean("hasTakenOverClub", false)) null
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
