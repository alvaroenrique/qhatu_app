package com.example.qhatu.data

import android.util.Log
import com.example.qhatu.ui.model.User
import com.example.qhatu.ui.model.UserInfo
import com.google.firebase.firestore.FirebaseFirestore

class RegisterValidatorRepository {

    private val databaseReference = FirebaseFirestore.getInstance()

    // Valida de DNI e Email esten en un documento de activecustomers
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
                    Log.d("ActiveCustomerLog::", user.toString())
                    blockVM(user)
                    block(true)
                } else {
                    Log.d("ActiveCustomerLog::", "No existe usuario en Active Customer Collection")
                    blockVM(User())
                    block(false)
                }
            }.addOnFailureListener {
                Log.d("ActiveCustomerLog::", it.message!!)
                blockVM(User())
                block(false)
            }
    }

}