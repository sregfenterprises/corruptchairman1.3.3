package com.sregfenterprises.corruptchairman.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sregfenterprises.corruptchairman.model.SeasonStats

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromSeasonStatsList(value: MutableList<SeasonStats>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSeasonStatsList(value: String): MutableList<SeasonStats> {
        val listType = object : TypeToken<MutableList<SeasonStats>>() {}.type
        return gson.fromJson(value, listType) ?: mutableListOf()
    }
}
