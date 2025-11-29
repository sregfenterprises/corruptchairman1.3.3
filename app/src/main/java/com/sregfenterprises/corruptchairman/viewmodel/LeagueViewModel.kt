package com.sregfenterprises.corruptchairman.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.data.ClubDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel to expose leagues for selection.
 * Leagues are now derived directly from the JSON-assigned clubs in the database.
 */
class LeagueViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LEAGUE_VIEWMODEL"

    private val repo = ClubRepository(
        ClubDatabase.getDatabase(application).clubDao(),
        application
    )

    private val _leagues = MutableStateFlow<List<String>>(emptyList())
    val leagues: StateFlow<List<String>> = _leagues

    init {
        Log.d(TAG, "Initializing LeagueViewModel with JSON-assigned leagues")

        viewModelScope.launch {
            // Ensure database has clubs
            repo.initializeDataIfNeeded()

            // Collect clubs from repository and extract leagues
            repo.getAllClubs().collect { clubs ->
                if (clubs.isNotEmpty()) {
                    // Extract unique league names from clubs
                    val leagueNames = clubs.map { it.league }.distinct()
                    _leagues.value = leagueNames
                    Log.d(TAG, "Leagues loaded: $leagueNames")
                } else {
                    Log.d(TAG, "No clubs found, leagues list is empty")
                }
            }
        }
    }
}
