package com.sregfenterprises.corruptchairman.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class Club(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val league: String = "",
    val country: String = "",
    val continent: String = "",
    val city: String = "",
    val population: Int = 0,
    val kitColor: String = "gray"
)
