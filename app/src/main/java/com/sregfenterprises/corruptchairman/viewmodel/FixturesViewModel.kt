package com.sregfenterprises.corruptchairman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.model.FixtureGenerator
import com.sregfenterprises.corruptchairman.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FixturesViewModel(
    private val repository: ClubRepository
) : ViewModel() {

    private val _fixtures = MutableStateFlow<List<Match>>(emptyList())
    val fixtures: StateFlow<List<Match>> = _fixtures

    fun loadFixtures() {
        viewModelScope.launch {

            // Get clubs only once from database (fix for mismatch error)
            val clubs = repository.getAllClubs().first()

            if (clubs.isNotEmpty()) {

                // Replace with real calendar logic later
                val matchDays = listOf(
                    "2025-12-01", "2025-12-08", "2025-12-15",
                    "2025-12-22", "2025-12-29"
                )

                val generatedFixtures = FixtureGenerator.generateLeagueFixtures(
                    clubs = clubs,
                    startDate = "2025-12-01",
                    matchDays = matchDays
                )

                _fixtures.value = generatedFixtures
            }
        }
    }
}
