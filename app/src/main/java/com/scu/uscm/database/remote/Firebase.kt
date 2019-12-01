package com.scu.uscm.database.remote

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.scu.uscm.database.model.Student

class Firebase private constructor() {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Firebase() }
    }

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getFirestore(): FirebaseFirestore = db

    fun setStudent(student: Student) {
        db.collection("students").document(student.id)
            .set(student)
            .addOnSuccessListener {
                OnSuccessListener<DocumentReference> { reference ->
                    Log.d("TAG", "Student successfully written! " + reference?.id)
                }
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }
}