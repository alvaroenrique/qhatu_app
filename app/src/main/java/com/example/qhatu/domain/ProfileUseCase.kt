package com.example.qhatu.domain

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.model.User
import com.example.qhatu.ui.model.UserInfo
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProfileUseCase(private val model: MainActivityViewModel, private val context: Context) {

    private val fireStoreRepository = FirestoreRepository()

    private val userId = model.getUid().value

    fun setUserData() {
        setCturrentUser()
        setCurrentUserPicture()
    }

    private fun setCturrentUser() {
        if (userId != null) {
            fireStoreRepository.getUserRefById(userId)
                .get()
                .addOnSuccessListener { result ->
                    result
                    val userData: DocumentReference? = result.data?.get("customer") as DocumentReference
                    userData?.get()?.addOnSuccessListener { res ->
                        val customerUserData: UserInfo? = res.toObject(UserInfo::class.java)

                        if (customerUserData != null) {
                            model.setUser(customerUserData)
                        }

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
                urlTask.addOnSuccessListener {
                        model.setUserPicture(it)
                }
            }
            .addOnFailureListener {
                Log.i("Error", "No existe imagen")
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

    fun updateUserProfileData(
        newName: String,
        newLastName: String,
        newPhone: Long,
        newEmail: String
    ) {
        if (userId != null) {
            fireStoreRepository.getUserRefById(userId)
                .get()
                .addOnSuccessListener { result ->
                    result
                    val userData: DocumentReference? = result.data?.get("customer") as DocumentReference
                    userData?.update(
                        mapOf(
                            "nombre" to newName,
                            "apellidos" to newLastName,
                            "celular" to newPhone,
                            "mail" to newEmail
                        )
                    )?.addOnSuccessListener {
                        val currentUser: UserInfo? = model.getUser().value
                        if (currentUser != null) {
                            currentUser.nombre = newName
                            currentUser.apellidos = newLastName
                            currentUser.celular = newPhone
                            currentUser.mail = newEmail

                            model.setUser(currentUser)
                        }
                        Toast.makeText(
                            context,
                            "Los datos fueron actualizados correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Ha ocurrido un error",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

    }


}