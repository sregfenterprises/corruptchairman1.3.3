package com.sregfenterprises.corruptchairman.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sregfenterprises.corruptchairman.data.Converters

@Entity(tableName = "clubs")
@TypeConverters(Converters::class) // for history serialization
data class Club(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    var league: String = "",
    val country: String = "",
    val continent: String = "",
    val city: String = "",
    val population: Int = 0,
    val rankingPoints: Int = 0,
    val kitColor: String = "gray",  // ✅ Fixed missing comma
    var played: Int = 0,
    var won: Int = 0,
    var drawn: Int = 0,
    var lost: Int = 0,
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0,
    var points: Int = 0,
    val history: MutableList<SeasonStats> = mutableListOf() // ✅ Will need TypeConverter
)
