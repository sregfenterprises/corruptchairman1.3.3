package com.sregfenterprises.corruptchairman.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sregfenterprises.corruptchairman.data.ClubDatabase
import com.sregfenterprises.corruptchairman.data.ClubRepository
import com.sregfenterprises.corruptchairman.model.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TakeoverViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: ClubRepository

    init {
        val dao = ClubDatabase.Companion.getDatabase(application).clubDao()
        repo = ClubRepository(dao, application)

        // Initialize DB if needed
        viewModelScope.launch {
            repo.initializeDataIfNeeded()
            // Automatically load continents after initialization
            observeContinents()
        }
    }

    private val _continents = MutableStateFlow<List<String>>(emptyList())
    val continents: StateFlow<List<String>> get() = _continents

    private val _countries = MutableStateFlow<List<String>>(emptyList())
    val countries: StateFlow<List<String>> get() = _countries

    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> get() = _clubs

    private val _selectedClub = MutableStateFlow<Club?>(null)
    val selectedClub: StateFlow<Club?> get() = _selectedClub

    /** Observes continents from repository Flow */
    private fun observeContinents() {
        viewModelScope.launch {
            repo.getContinents().collectLatest { list ->
                _continents.value = list
            }
        }
    }

    /** Observes countries for a selected continent */
    fun loadCountries(continent: String) {
        viewModelScope.launch {
            repo.getCountriesByContinent(continent).collectLatest { list ->
                _countries.value = list
            }
        }
    }

    /** Observes clubs for a selected country */
    fun loadClubs(country: String) {
        viewModelScope.launch {
            repo.getClubsByCountry(country).collectLatest { list ->
                _clubs.value = list


            }
        }
    }

    /** Select a club */
    fun selectClub(club: Club) {
        _selectedClub.value = club
    }
}