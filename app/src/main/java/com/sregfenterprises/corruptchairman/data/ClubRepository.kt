package com.sregfenterprises.corruptchairman.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sregfenterprises.corruptchairman.model.Club
import com.sregfenterprises.corruptchairman.model.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ClubRepository(
    private val dao: ClubDao,
    private val context: Context
) {

    private val TAG = "ClubRepository"
    private val PREFS_NAME = "fixtures_data"
    private val FIXTURES_KEY = "fixtures_json"
    private val gson = Gson()

    // ---------------------------
    // INITIAL CLUB SETUP
    // ---------------------------

    suspend fun initializeDataIfNeeded() {
        val continentsCount = dao.getContinents().first().size
        if (continentsCount == 0) {
            Log.d(TAG, "Database empty. Loading clubs from JSON...")
            val clubs = loadClubsFromJson()
            dao.insertClubs(clubs)
            Log.d(TAG, "Inserted ${clubs.size} clubs into DB")
        }
    }

    private suspend fun loadClubsFromJson(): List<Club> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("clubs.json")
            .bufferedReader()
            .use { it.readText() }
        val clubType = object : TypeToken<List<Club>>() {}.type
        Gson().fromJson(jsonString, clubType)
    }

    fun getContinents(): Flow<List<String>> =
        dao.getContinents().flowOn(Dispatchers.IO)

    fun getCountriesByContinent(continent: String): Flow<List<String>> =
        dao.getCountriesByContinent(continent).flowOn(Dispatchers.IO)

    fun getClubsByCountry(country: String): Flow<List<Club>> =
        dao.getClubsByCountry(country).flowOn(Dispatchers.IO)

    fun getAllClubs(): Flow<List<Club>> =
        dao.getAllClubs().flowOn(Dispatchers.IO)

    suspend fun assignLeagueToClubs(clubs: List<Club>, leagueName: String) {
        clubs.forEach { it.league = leagueName }
        dao.insertClubs(clubs)
    }

    fun getClubsByLeague(leagueName: String): Flow<List<Club>> =
        dao.getClubsByLeague(leagueName).flowOn(Dispatchers.IO)


    // ---------------------------------------------------------
    // 🔥 FIXTURE STORAGE SYSTEM (SharedPreferences + Gson)
    // ---------------------------------------------------------

    /** Save fixtures to SharedPreferences */
    suspend fun saveFixtures(fixtures: List<Match>) = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(fixtures)
        prefs.edit().putString(FIXTURES_KEY, json).apply()
        Log.d(TAG, "Saved ${fixtures.size} fixtures")
    }

    /** Load fixtures from SharedPreferences */
    fun getAllFixtures(): Flow<List<Match>> = flow {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FIXTURES_KEY, null)

        if (json == null) {
            emit(emptyList())
        } else {
            val type = object : TypeToken<List<Match>>() {}.type
            val fixtures: List<Match> = gson.fromJson(json, type)
            emit(fixtures)
        }
    }.flowOn(Dispatchers.IO)

    /** Helper to load once synchronously (not reactive) */
    suspend fun loadFixturesOnce(): List<Match> = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FIXTURES_KEY, null) ?: return@withContext emptyList()
        val type = object : TypeToken<List<Match>>() {}.type
        gson.fromJson(json, type)
    }
}
