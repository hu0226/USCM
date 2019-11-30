package com.scu.uscm.database.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scu.uscm.database.model.Student

class Firebase private constructor() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Firebase() }
    }


    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG: String = "Firebase"

    fun getFirestore(): FirebaseFirestore = db

    fun setStudent(student: Student) {
        db.collection("students").document(student.id)
            .set(student)
            .addOnSuccessListener { Log.d("Jung", "Student successfully written!") }
            .addOnFailureListener { e -> Log.d("Jung", "$e") }
    }
}