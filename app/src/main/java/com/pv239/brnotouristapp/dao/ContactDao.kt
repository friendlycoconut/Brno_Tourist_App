package com.pv239.brnotouristapp.dao

import androidx.room.*
import com.pv239.brnotouristapp.data.ContactEntity

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: List<ContactEntity>)

    @Query("SELECT * FROM ContactEntity ORDER BY id ASC")
    fun selectAllOrderById(): List<ContactEntity>

    @Delete
    fun delete(entity: ContactEntity)
}