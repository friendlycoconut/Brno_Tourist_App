package com.pv239.brnotouristapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pv239.brnotouristapp.api.data.FeaturePointEntity
import com.pv239.brnotouristapp.dao.FeatureDao


@Database(
    entities = [FeaturePointEntity::class],
    version = 1
)
abstract class FeatureDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "feature.db"

        fun create(context: Context): FeatureDatabase =
            Room.databaseBuilder(context, FeatureDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
    }

    abstract fun featureDao(): FeatureDao
}