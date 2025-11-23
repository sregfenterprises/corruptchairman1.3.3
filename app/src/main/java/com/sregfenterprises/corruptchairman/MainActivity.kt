package com.sregfenterprises.corruptchairman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.data.ClubDatabase
import com.sregfenterprises.corruptchairman.model.Club
import com.sregfenterprises.corruptchairman.profile.UserProfileManager
import com.sregfenterprises.corruptchairman.profile.ChairmanProfileScreen
import com.sregfenterprises.corruptchairman.profile.CreateProfileScreen
import com.sregfenterprises.corruptchairman.ui.clubdetail.ClubDetailScreen
import com.sregfenterprises.corruptchairman.ui.home.HomeScreen
import com.sregfenterprises.corruptchairman.ui.mainscreen.MainScreen
import com.sregfenterprises.corruptchairman.ui.takeover.TakeoverTeamScreen
import com.sregfenterprises.corruptchairman.ui.theme.CorruptChairmanTheme
import com.sregfenterprises.corruptchairman.ui.welcome.WelcomeScreen
import com.sregfenterprises.corruptchairman.viewmodel.TakeoverViewModel

class MainActivity : ComponentActivity() {

    private val takeoverViewModel: TakeoverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserProfileManager.init(applicationContext)

        val database = ClubDatabase.getDatabase(this)
        val repository = ClubRepository(database.clubDao(), this)

        setContent {
            CorruptChairmanTheme {

                var currentScreen by remember { mutableStateOf("welcome") }
                var userHasProfile by remember { mutableStateOf(UserProfileManager.hasProfile()) }
                var selectedClub by remember { mutableStateOf<Club?>(null) }

                // Auto-jump to MainScreen if takeover already happened
                LaunchedEffect(Unit) {
                    val prefs = applicationContext.getSharedPreferences("game_data", MODE_PRIVATE)
                    if (prefs.getBoolean("hasTakenOverClub", false)) {

                        selectedClub = Club(
                            name = prefs.getString("clubName", "") ?: "",
                            league = prefs.getString("league", "") ?: "",
                            continent = prefs.getString("continent", "") ?: "",
                            country = prefs.getString("country", "") ?: "",
                            population = prefs.getInt("population", 0),
                            city = prefs.getString("city", "") ?: "",
                            kitColor = prefs.getString("kitColor", "#FF0000") ?: "#FF0000"
                        )

                        currentScreen = "main"
                    }
                }

                when (currentScreen) {

                    // 1️⃣ Welcome Screen
                    "welcome" -> WelcomeScreen(
                        onContinue = {
                            currentScreen =
                                if (userHasProfile) "home"
                                else "createProfile"
                        }
                    )

                    // 2️⃣ Create Profile
                    "createProfile" -> CreateProfileScreen(
                        clubRepository = repository,
                        onProfileCreated = {
                            userHasProfile = true
                            currentScreen = "home"
                        },
                        onBack = { currentScreen = "welcome" }
                    )

                    // 3️⃣ Home Screen
                    "home" -> HomeScreen(
                        onTakeoverTeamClicked = {
                            currentScreen = "takeover"
                        }
                    )

                    // 4️⃣ Takeover Team Selection (single-screen flow)
                    "takeover" -> {
                        TakeoverTeamScreen(
                            viewModel = takeoverViewModel,
                            onBack = { currentScreen = "home" },
                            onClubSelected = { club ->
                                selectedClub = club
                                currentScreen = "summary"
                            }
                        )
                    }

                    // 5️⃣ Club Summary / Takeover Confirmation
                    "summary" -> selectedClub?.let { club ->
                        ClubDetailScreen(
                            club = club,
                            showTakeoverButton = true,
                            onTakeoverConfirmed = {
                                currentScreen = "main"
                            }
                        )
                    }

                    // 6️⃣ Main Screen
                    "main" -> MainScreen(
                        onChairmanProfileClicked = {
                            currentScreen = "chairmanProfile"
                        }
                    )

                    // 7️⃣ Chairman Profile
                    "chairmanProfile" -> ChairmanProfileScreen(
                        onBack = { currentScreen = "main" }
                    )
                }
            }
        }
    }
}
