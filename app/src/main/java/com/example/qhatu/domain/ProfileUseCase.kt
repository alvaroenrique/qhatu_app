package com.example.qhatu.domain

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.dao.User
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream

class ProfileUseCase(private val model: MainActivityViewModel) {

    private val fireStoreRepository = FirestoreRepository()

    private val userId = "8HweNAVEWwkeKjru0CZH"

    fun setUserData() {
        setCturrentUser()
        setCurrentUserPicture()
    }

    private fun setCturrentUser() {
        fireStoreRepository.getUserRefById(userId)
            .get()
            .addOnSuccessListener { result ->
                result
                val userData: DocumentReference? = result.data?.get("customer") as DocumentReference
                userData?.get()?.addOnSuccessListener { res ->
                    val customerUserData: User? = res.toObject(User::class.java)

                    if (customerUserData != null) {
                        model.setUser(customerUserData)
                    }

                }
            }
    }

    private fun setCurrentUserPicture() {
        FirebaseStorage.getInstance()
            .reference
            .child("pics/${userId}")
            .downloadUrl
            .addOnCompleteListener { urlTask ->
                urlTask.result?.let {
                    model.setUserPicture(it)
                }
            }
    }

    fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child("pics/${userId}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)

        upload.addOnCompleteListener { uploadTask ->
            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        model.setUserPicture(it)

                    }
                }
            }
        }

    }


}