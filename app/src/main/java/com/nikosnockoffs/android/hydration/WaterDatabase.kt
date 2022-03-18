package com.nikosnockoffs.android.hydration

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// class to ensure we only create one database
// make it abstract and not instantiate it directly - room will subclass it for us
// when db is created, list of entities will be consulted, room will look at properties of entity
// and will use that to create the table with the correct columns in the db
@Database(entities = [WaterRecord::class], version = 1)
abstract class WaterDatabase: RoomDatabase() {

    abstract fun waterDao(): WaterDao


    companion object {

        // singleton pattern - make it a private object so can only initialize if it doesn't exist or return existing copy
        @Volatile
        private var INSTANCE: WaterDatabase? = null

        fun getDatabase(context: Context): WaterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WaterDatabase::class.java,
                    "water_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}