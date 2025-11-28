package com.sregfenterprises.corruptchairman.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sregfenterprises.corruptchairman.model.Club
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    // Insert or update clubs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs: List<Club>)

    // Update a single club (needed if you assign a league)
    @Update
    suspend fun updateClub(club: Club)

    // Update multiple clubs
    @Update
    suspend fun updateClubs(clubs: List<Club>)

    // Reactive list of continents
    @Query("SELECT DISTINCT continent FROM clubs ORDER BY continent ASC")
    fun getContinents(): Flow<List<String>>

    // Reactive list of countries for selected continent
    @Query("SELECT DISTINCT country FROM clubs WHERE continent = :continent ORDER BY country ASC")
    fun getCountriesByContinent(continent: String): Flow<List<String>>

    // Reactive list of clubs for selected country
    @Query("SELECT * FROM clubs WHERE country = :country ORDER BY name ASC")
    fun getClubsByCountry(country: String): Flow<List<Club>>

    // Get all clubs (needed for league generation)
    @Query("SELECT * FROM clubs ORDER BY rankingPoints DESC")
    fun getAllClubs(): Flow<List<Club>>

    // Get all clubs for a specific league
    @Query("SELECT * FROM clubs WHERE league = :leagueName ORDER BY rankingPoints DESC")
    fun getClubsByLeague(leagueName: String): Flow<List<Club>>
}
