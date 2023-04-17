//package com.pv239.brnotouristapp.dao
//
//import androidx.room.*
//import com.pv239.brnotouristapp.data.PlaceEntity
//
//@Dao
//interface PlaceDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun persist(entity: PlaceEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun persist(entity: List<PlaceEntity>)
//
//    @Query("SELECT * FROM PlaceEntity ORDER BY id ASC")
//    fun selectAllOrderById(): List<PlaceEntity>
//
//    @Delete
//    fun delete(entity: PlaceEntity)
//}