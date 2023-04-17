//package com.pv239.brnotouristapp.dao
//
//import androidx.room.*
//import com.pv239.brnotouristapp.data.AddressEntity
//
//@Dao
//interface AddressDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun persist(entity: AddressEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun persist(entity: List<AddressEntity>)
//
//    @Query("SELECT * FROM AddressEntity ORDER BY id ASC")
//    fun selectAllOrderById(): List<AddressEntity>
//
//    @Delete
//    fun delete(entity: AddressEntity)
//}