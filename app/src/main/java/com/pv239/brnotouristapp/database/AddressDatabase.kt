package com.pv239.brnotouristapp.database

import android.content.Context
import android.location.Address
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pv239.brnotouristapp.dao.AddressDao
import com.pv239.brnotouristapp.dao.ContactDao
import com.pv239.brnotouristapp.data.AddressEntity
import com.pv239.brnotouristapp.data.ContactEntity

@Database(
    entities = [AddressEntity::class],
    version = 1
)
abstract class AddressDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "address.db"

        fun create(context: Context): AddressDatabase =
            Room.databaseBuilder(context, AddressDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
    }

    abstract fun addressDao(): AddressDao
}