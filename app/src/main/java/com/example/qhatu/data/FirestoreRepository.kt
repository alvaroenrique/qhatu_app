package com.example.qhatu.data


import android.util.Log
import com.example.qhatu.model.DeliveryDate
import com.example.qhatu.model.DeliveryDateInfo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    val db = FirebaseFirestore.getInstance()

    fun getUserRefById(id: String): DocumentReference {
        return db.collection("users").document(id)
    }

    fun getMeetingDatesRef(): CollectionReference {
        return db.collection("meeting-dates")
    }

    fun getDeliverDatesAvailable(
        block: (deliverDateList: ArrayList<DeliveryDate>) -> Unit,
        blockError: (error: String) -> Unit
    ) {
        db.collection("deliver-dates").whereEqualTo("available", true).get().addOnSuccessListener {
            if (!it.isEmpty) {
                val availableDeliverDates = ArrayList<DeliveryDate>()
                for (doc in it) {
                    Log.d("debuggy", doc.data.toString())
                    val deliverDateInfo = doc.toObject(DeliveryDateInfo::class.java)
                    val deliverDate = DeliveryDate(doc.id, doc.reference, deliverDateInfo)
                    availableDeliverDates.add(deliverDate)
                }
                block(availableDeliverDates)
            } else {
                val availableDeliverDates = ArrayList<DeliveryDate>()
                block(availableDeliverDates)
            }
        }.addOnFailureListener {
            blockError(it.message.toString())
        }
    }

}