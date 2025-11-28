package com.sregfenterprises.corruptchairman.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sregfenterprises.corruptchairman.model.Club
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ClubRepository(
    private val dao: ClubDao,
    private val context: Context
) {

    private val TAG = "ClubRepository"

    /**
     * Initialize the database from JSON if it's empty
     */
    suspend fun initializeDataIfNeeded() {
        val continentsCount = dao.getContinents().first().size
        if (continentsCount == 0) {
            Log.d(TAG, "Database empty. Loading clubs from JSON...")
            val clubs = loadClubsFromJson()
            dao.insertClubs(clubs)
            Log.d(TAG, "Inserted ${clubs.size} clubs into DB")
        } else {
            Log.d(TAG, "Database already has data. Skipping initialization.")
        }
    }

    /**
     * Load club data from assets/clubs.json
     */
    private suspend fun loadClubsFromJson(): List<Club> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("clubs.json")
            .bufferedReader()
            .use { it.readText() }
        val clubType = object : TypeToken<List<Club>>() {}.type
        val clubs: List<Club> = Gson().fromJson(jsonString, clubType)
        Log.d(TAG, "Parsed ${clubs.size} clubs from JSON")
        clubs
    }

    /**
     * Reactive list of continents
     */
    fun getContinents(): Flow<List<String>> {
        return dao.getContinents().flowOn(Dispatchers.IO)
    }

    /**
     * Reactive list of countries for a continent
     */
    fun getCountriesByContinent(continent: String): Flow<List<String>> {
        return dao.getCountriesByContinent(continent).flowOn(Dispatchers.IO)
    }

    /**
     * Reactive list of clubs for a country
     */
    fun getClubsByCountry(country: String): Flow<List<Club>> {
        return dao.getClubsByCountry(country).flowOn(Dispatchers.IO)
    }

    /**
     * Get all clubs (needed for league generation)
     */
    fun getAllClubs(): Flow<List<Club>> {
        return dao.getAllClubs().flowOn(Dispatchers.IO)
    }

    /**
     * Assign a league to a list of clubs and persist in the database
     */
    suspend fun assignLeagueToClubs(clubs: List<Club>, leagueName: String) {
        Log.d(TAG, "Assigning league '$leagueName' to ${clubs.size} clubs")
        clubs.forEach { it.league = leagueName }
        dao.insertClubs(clubs) // Upsert with REPLACE strategy
        Log.d(TAG, "League '$leagueName' assigned to clubs and persisted")
    }

    /**
     * Optional: get all clubs in a specific league
     */
    fun getClubsByLeague(leagueName: String): Flow<List<Club>> {
        return dao.getClubsByLeague(leagueName).flowOn(Dispatchers.IO)
    }
}
