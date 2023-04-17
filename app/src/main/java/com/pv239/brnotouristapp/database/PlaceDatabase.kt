package com.pv239.brnotouristapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pv239.brnotouristapp.dao.PlaceDao
import com.pv239.brnotouristapp.data.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 1
)
abstract class PlaceDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "place.db"

        fun create(context: Context): PlaceDatabase =
            Room.databaseBuilder(context, PlaceDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
    }

    abstract fun placeDao(): PlaceDao
}