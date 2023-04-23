package com.pv239.brnotouristapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pv239.brnotouristapp.api.data.FeaturePointEntity

// annotation for dao class.
@Dao
interface FeatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: FeaturePointEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: List<FeaturePointEntity>)

    @Query("SELECT * FROM featureTable ORDER BY id ASC")
    fun selectAllPoints(): List<FeaturePointEntity>
}