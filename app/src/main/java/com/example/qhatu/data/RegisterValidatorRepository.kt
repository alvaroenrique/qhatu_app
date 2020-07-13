package com.example.qhatu.data

import android.util.Log
import com.example.qhatu.ui.model.User
import com.example.qhatu.ui.model.UserInfo
import com.google.firebase.firestore.FirebaseFirestore

class RegisterValidatorRepository {

    private val databaseReference = FirebaseFirestore.getInstance()

    fun firebaseValidateExistenceInActiveCustomers(
        DNI: String,
        email: String,
        block: (found: Boolean) -> Unit,
        blockVM: (user: User) -> Unit
    ) {
        val collection = databaseReference.collection("active-customers")

        collection.whereEqualTo("DNI", DNI.toLong()).whereEqualTo("mail", email).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val activeCustomerDoc = it.documents[0]
                    val activeCustomerDocID = it.documents[0].id
                    val activeCustomerDocRef = it.documents[0].reference
                    Log.d("ActiveCustomerLog::", activeCustomerDoc.toString())
                    Log.d("ActiveCustomerLog::", activeCustomerDocID)
                    Log.d("ActiveCustomerLog::", activeCustomerDocRef.toString())
                    val user = User(
                        userInfo = activeCustomerDoc.toObject(UserInfo::class.java),
                        userDocumentId = activeCustomerDocID,
                        userReference = activeCustomerDocRef
                    )
                    block(true)
                    blockVM(user)
                } else {
                    Log.d("ActiveCustomerLog::", "No existe usuario en Active Customer Collection")
                    block(false)
                    blockVM(User())
                }
            }.addOnFailureListener {
                Log.d("ActiveCustomerLog::", it.message!!)
                block(false)
                blockVM(User())
            }
    }

}