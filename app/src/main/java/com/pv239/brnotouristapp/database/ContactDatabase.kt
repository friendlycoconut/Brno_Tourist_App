//package com.pv239.brnotouristapp.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.pv239.brnotouristapp.dao.ContactDao
//import com.pv239.brnotouristapp.data.ContactEntity
//
//@Database(
//    entities = [ContactEntity::class],
//    version = 1
//)
//abstract class ContactDatabase : RoomDatabase() {
//
//    companion object {
//        private const val NAME = "contact.db"
//
//        fun create(context: Context): ContactDatabase =
//            Room.databaseBuilder(context, ContactDatabase::class.java, NAME)
//                .allowMainThreadQueries()
//                .build()
//    }
//
//    abstract fun contactDao(): ContactDao
//}