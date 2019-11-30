package com.scu.uscm.database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scu.uscm.database.model.Student

@Dao
interface UscmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Query("SELECT * FROM Student")
    fun getStudent(): Student

    @Query("DELETE FROM Student")
    fun clearStudent()
}