package com.example.qhatu.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    val db = FirebaseFirestore.getInstance()

    fun getUserRefById(id: String): DocumentReference {
        return db.collection("users").document(id)
    }

}