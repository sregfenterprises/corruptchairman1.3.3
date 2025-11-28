package com.sregfenterprises.corruptchairman.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.data.ClubDatabase
import com.sregfenterprises.corruptchairman.model.League
import com.sregfenterprises.corruptchairman.model.LeagueGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeagueViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LEAGUE_VIEWMODEL"

    private val repo: ClubRepository =
        ClubRepository(ClubDatabase.getDatabase(application).clubDao(), application)

    private val _leagues = MutableStateFlow<List<League>>(emptyList())
    val leagues: StateFlow<List<League>> = _leagues

    init {
        Log.d(TAG, "Initializing LeagueViewModel and launching league generator")

        viewModelScope.launch {
            // Ensure database has clubs
            Log.d(TAG, "Initializing database if needed...")
            repo.initializeDataIfNeeded()

            Log.d(TAG, "Fetching all clubs from repository...")
            repo.getAllClubs().collect { clubs ->
                Log.d(TAG, "Fetched ${clubs.size} clubs from repository")
                if (clubs.isNotEmpty()) {

                    // 1️⃣ World Super League
                    Log.d(TAG, "Creating World Super League...")
                    val worldLeague = LeagueGenerator.createTop18WorldLeague(clubs)

                    // Persist league assignments
                    repo.assignLeagueToClubs(worldLeague.clubs, worldLeague.name)

                    // 2️⃣ European Super League, exclude World Super League clubs
                    Log.d(TAG, "Creating European Super League...")
                    val europeLeague = LeagueGenerator.createTop18ContinentLeague(
                        clubs,
                        continent = "Europe",
                        leagueName = "European Super League",
                        excludedClubs = worldLeague.clubs.toSet() // prevent duplicates
                    )

                    // Persist league assignments
                    repo.assignLeagueToClubs(europeLeague.clubs, europeLeague.name)

                    // Update leagues in StateFlow
                    _leagues.value = listOf(worldLeague, europeLeague)
                    Log.d(TAG, "Leagues list updated in StateFlow: ${_leagues.value.size} leagues")
                } else {
                    Log.d(TAG, "No clubs available, cannot generate leagues")
                }
            }
        }
    }
}
