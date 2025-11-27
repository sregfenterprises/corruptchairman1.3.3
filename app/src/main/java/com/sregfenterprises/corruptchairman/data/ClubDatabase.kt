package com.sregfenterprises.corruptchairman.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sregfenterprises.corruptchairman.model.Club

@Database(entities = [Club::class], version = 2, exportSchema = false)
abstract class ClubDatabase : RoomDatabase() {

    abstract fun clubDao(): ClubDao

    companion object {
        @Volatile
        private var INSTANCE: ClubDatabase? = null

        fun getDatabase(context: Context): ClubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClubDatabase::class.java,
                    "club_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}