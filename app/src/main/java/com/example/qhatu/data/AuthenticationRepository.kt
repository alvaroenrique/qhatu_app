package com.example.qhatu.data

import android.util.Log
import com.example.qhatu.ui.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.lang.reflect.Executable

class AuthenticationRepository {

    // Se declaran las variables del repositorio
    private val mauth = FirebaseAuth.getInstance()
    private val mDatabaseReference = FirebaseFirestore.getInstance()

    // Se declaran los metodos del repositorio - Los repositorios son los encargados de comunicarse con los servicios externos

    // Crea un usuario con email y contrasena en firebase y retorna el usuario creado o un error
    fun firebaseCreateUserWithEmailAndPassword(
        email: String,
        password: String,
        user: User,
        block: (user: User) -> Unit,
        blockError: (error: String) -> Unit
    ) {
        mauth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val firebaseUser = mauth.currentUser
            if (firebaseUser != null) {
                user.uid = firebaseUser.uid
                user.email = firebaseUser.email
                user.isAuthenticated = true
                block(user)
                Log.d("SignUpLog::", "User Created")
            } else {
                block(user)
                Log.d("SignUpLog::", "User not Created")
            }
        }.addOnFailureListener {
            block(user)
            blockError(it.message!!)
            Log.d("SignUpLog::", "Error ${it.message!!}")
        }
    }

    fun firebaseCreateUserDocInFirestore(user: User, block: (done: Boolean) -> Unit) {
        val userRef = hashMapOf(
            "customer" to user.userReference
        )
        mDatabaseReference.collection("users").document(user.uid!!).set(userRef)
            .addOnSuccessListener {
                block(true)
            }.addOnFailureListener {
                block(false)
            }
    }

    fun firebaseSignOut(): Boolean {
        try {
            mauth.signOut()
            return true
        } catch (ex: Exception) {
            Log.d("SignOutLog::", ex.message!!)
            return false
        }
    }
}