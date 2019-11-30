package com.scu.uscm.database.remote

import com.google.firebase.firestore.FirebaseFirestore

class Firebase private constructor() {
    companion object {
        val intance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Firebase() }
    }

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getFirestore(): FirebaseFirestore = db
}