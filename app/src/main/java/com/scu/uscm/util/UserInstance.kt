package com.scu.uscm.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scu.uscm.USCMApplication.Companion.appContext
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object UserInstance {

    private val db = UscmDatabase.getInstance(appContext)

    private val _student = MutableLiveData<Student>()

    val student: LiveData<Student>
        get() = _student

    fun getUser() {
        GlobalScope.launch {
            val ss: Student? = db.uscmDao().getStudent()

            withContext(Dispatchers.Main) {
                _student.value = ss
            }
        }
    }
}