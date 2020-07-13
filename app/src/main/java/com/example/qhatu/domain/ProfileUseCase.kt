package com.example.qhatu.domain

import android.util.Log
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.model.dao.User
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.google.firebase.firestore.DocumentReference

class ProfileUseCase(private val userId: String, private val model: MainActivityViewModel) {

    private val fireStoreRepository = FirestoreRepository()

    init {
        setCturrentUser()
    }

    private fun setCturrentUser() {
        fireStoreRepository.getUserRefById(userId)
            .get()
            .addOnSuccessListener { result ->
                val userData: DocumentReference? = result.data?.get("customer") as DocumentReference
                userData?.get()?.addOnSuccessListener { res ->
                    val customerUserData: User? = res.toObject(User::class.java)

                    if (customerUserData != null) {
                        model.setUser(customerUserData)
                    }

                }
            }
    }







}