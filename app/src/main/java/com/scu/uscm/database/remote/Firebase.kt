package com.scu.uscm.database.remote

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.scu.uscm.R
import com.scu.uscm.database.model.Schedule
import com.scu.uscm.database.model.Student

class Firebase private constructor() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Firebase() }
    }

    private val TAG = "Firebae"

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var liveSignHistory = MutableLiveData<MutableList<Schedule>>()

    fun setStudent(student: Student) {
        db.collection("students").document(student.id)
            .set(student)
            .addOnSuccessListener { Log.d(TAG, "Student successfully written!") }
            .addOnFailureListener { e -> Log.d(TAG, "Error updating document", e) }
    }

    fun setSchedual(studentId: String, schedule: Schedule) {
        db.collection("students").document(studentId)
            .collection("schedule").document(schedule.classTime)
            .set(schedule)
            .addOnSuccessListener { Log.d(TAG, "Student successfully written!") }
            .addOnFailureListener { e -> Log.d(TAG, "Error updating document", e) }
    }

    fun signClass(studentId: String, currentHour: String, currentTime: String, context: Context, navController: NavController){
        db.collection("students").document(studentId)
            .collection("schedule")
            .whereEqualTo("classTime", currentHour)
            .get()
            .addOnSuccessListener { documents ->
                Log.w(TAG, documents.documentChanges.size.toString())
                for (document in documents) {
                    if (!document.toObject(Schedule::class.java).isSign!!) {
                        db.collection("students").document(studentId)
                            .collection("schedule").document(currentHour)
                            .update("sign", true,
                                "signTime", currentTime)
                            .addOnSuccessListener {
                                Toast.makeText(context, "點名成功", Toast.LENGTH_LONG).show()
                                navController.navigate(R.id.action_global_historyFragment)
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getSignHistory(studentId: String) {
        val signHistory: MutableList<Schedule> = mutableListOf()
        db.collection("students").document(studentId)
            .collection("schedule")
            .whereEqualTo("sign", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    signHistory.add(document.toObject(Schedule::class.java))
                }
                liveSignHistory.value = signHistory
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

}