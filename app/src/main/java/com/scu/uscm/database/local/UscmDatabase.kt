package com.scu.uscm.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scu.uscm.database.model.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class UscmDatabase : RoomDatabase() {

    abstract fun uscmDao(): UscmDao

    companion object {
        private var INSTANCE: UscmDatabase? = null

        fun getInstance(context: Context): UscmDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, UscmDatabase::class.java, "uscmDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as UscmDatabase
        }
    }
}