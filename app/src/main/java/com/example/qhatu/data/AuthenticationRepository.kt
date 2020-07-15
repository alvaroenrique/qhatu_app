package com.example.qhatu.data

import android.util.Log
import com.example.qhatu.ui.model.User
import com.example.qhatu.ui.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
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
                user.isCreated = true
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

    // Crea usuario en collection users de firestore con referencia al active customer
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

    // Login with email and password
    fun firebaseLogInWithEmailAndPassword(
        email: String,
        password: String,
        blockVM: (user: User) -> Unit,
        block: (success: Boolean) -> Unit
    ) {
        mauth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = User(it.user?.uid, it.user?.email, true, isCreated = false)
            Log.d("LoginLog::", "Loggeado")
            blockVM(user)
            block(true)
        }.addOnFailureListener {
            Log.d("LoginLog::", "No Loggeado")
            block(false)
        }
    }

    fun checkIfLogged(): String? {
        return mauth.currentUser?.uid
    }

    fun getUserInformation(uid:String, block: (user: User) -> Unit){
        mDatabaseReference.collection("users").document(uid).get().addOnSuccessListener {
            if (it!=null){
                Log.d("CheckedLog::", uid.toString())
                Log.d("CheckedLog::", it["customer"].toString())
                val customerReference = it["customer"] as DocumentReference
                Log.d("CheckedLog::", "Found")
                customerReference.get().addOnSuccessListener {
                    val newUser = User()
                    val uInfo = it.toObject(UserInfo::class.java)!!
                    newUser.userInfo = uInfo
                    newUser.email = uInfo.mail
                    newUser.userDocumentId = it.id
                    newUser.userReference = it.reference
                    block(newUser)
                }
            }
        }.addOnFailureListener {
            Log.d("CheckedLog::", "No se obtuvo nada")
        }
    }

    // Deslogeado
    fun firebaseSignOut(): Boolean {
        try {
            mauth.signOut()
            Log.i("quatu", "ENTRAAAA")
            return true
        } catch (ex: Exception) {
            Log.d("SignOutLog::", ex.message!!)
            return false
        }
    }
}