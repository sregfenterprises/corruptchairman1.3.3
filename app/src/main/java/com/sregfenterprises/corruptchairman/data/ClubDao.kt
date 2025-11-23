package com.sregfenterprises.corruptchairman.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sregfenterprises.corruptchairman.model.Club
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    // Insert all clubs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs: List<Club>)

    // Reactive list of continents
    @Query("SELECT DISTINCT continent FROM clubs ORDER BY continent ASC")
    fun getContinents(): Flow<List<String>>

    // Reactive list of countries for selected continent
    @Query("SELECT DISTINCT country FROM clubs WHERE continent = :continent ORDER BY country ASC")
    fun getCountriesByContinent(continent: String): Flow<List<String>>

    // Reactive list of clubs for selected country
    @Query("SELECT * FROM clubs WHERE country = :country ORDER BY name ASC")
    fun getClubsByCountry(country: String): Flow<List<Club>>
}
