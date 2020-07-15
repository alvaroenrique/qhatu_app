package com.example.qhatu.data

import android.util.Log
import com.example.qhatu.model.DeliveryDate
import com.example.qhatu.model.DeliveryDateInfo
import com.example.qhatu.model.HistoricalOrdeInfo
import com.example.qhatu.model.HistoricalOrder
import com.example.qhatu.ui.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    val db = FirebaseFirestore.getInstance()

    fun getUserRefById(id: String): DocumentReference {
        return db.collection("users").document(id)
    }

    fun getMeetingDatesRef(): CollectionReference {
        return db.collection("meeting-dates")
    }

    fun getProductCategoryRef(productId: String?): DocumentReference {
        if (productId != null) {
            return db.collection("product-categories").document(productId)
        }
        return db.collection("product-categories").document("")
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

    // Refactorizar!
    fun placeOrder(
        deliveryDate: DeliveryDate,
        paymentMethod: String,
        superMarket: String,
        block: (success: Boolean) -> Unit,
        user: User
    ) {
        Log.d("bbb", user.uid!!)
        val data = mapOf(
            "users" to FieldValue.arrayUnion(
                mapOf(
                    "reference" to user.userReference,
                    "paymentMethod" to paymentMethod,
                    "superMarket" to superMarket
                )
            ),
            "users_count" to FieldValue.increment(1)
        )
        db.collection("deliver-dates").document(deliveryDate.docId).update(data)
            .addOnSuccessListener {
                block(true)
            }.addOnFailureListener {
                Log.d("RequestOrderLOG::", it.message.toString())
                block(false)
            }

        var data1 = mapOf<Any, Any>()
        db.collection("users").document(user.uid!!).collection("purchase-list").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    Log.d("bbb", "hay")
                    var counter = 0
                    for (doc in it) {
                        data1 = data1.plus(counter.toString() to doc.data)
                        counter++
                    }
                    db.collection("users").document(user.uid!!).collection("orders").document()
                        .set(
                            mapOf(
                                "date" to deliveryDate.deliverDateInfo.date,
                                "purchaseList" to data1
                            )
                        )
                } else {
                    Log.d("bbb", "nada")
                }
            }
    }

    fun getHistoricalOrders(
        uid: String,
        block: (success: Boolean) -> Unit,
        blockF: (orderList: ArrayList<HistoricalOrder>) -> Unit,
        blockVM: (orderList: ArrayList<HistoricalOrder>) -> Unit
    ) {
        db.collection("users").document(uid).collection("orders").get().addOnSuccessListener {
            if (!it.isEmpty) {
                val orderList = arrayListOf<HistoricalOrder>()
                var counter = 0
                for (doc in it) {
                    val historicalOrdeInfo = doc.toObject(HistoricalOrdeInfo::class.java)
                    val historicalOrder =
                        HistoricalOrder(counter.toLong(), doc.id, historicalOrdeInfo)
                    orderList.add(historicalOrder)
                    counter++
                }
                blockF(orderList)
                blockVM(orderList)
                block(true)
            } else {
                block(false)
            }
        }.addOnFailureListener {
            block(false)
        }
    }
}