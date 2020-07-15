package com.example.qhatu.domain

import android.util.Log
import com.example.qhatu.data.AuthenticationRepository
import com.example.qhatu.data.PersistenceRepository
import com.example.qhatu.ui.authentication.activities.AuthenticationActivity
import com.example.qhatu.ui.model.User
import com.example.qhatu.ui.model.UserInfo
import java.util.*

class AuthenticationUseCase {

    // Se inicializa el repositorio a utilizar
    val authenticationRepository = AuthenticationRepository()
    //val persistenceRepository = PersistenceRepository()

    //val persistenceUseCase = PersistenceUseCase()

    // Se declaran los metodos del caso de uso que retornan lo que retornan las funciones del repositorio inicializado

    // Crea usuario en firebase y crea el objeto
    fun createUserAndSetObject(
        email: String,
        password: String,
        user: User,
        block: (user: User) -> Unit,
        blockError: (ex: String) -> Unit
    ) {
        authenticationRepository.firebaseCreateUserWithEmailAndPassword(
            email,
            password,
            user,
            block,
            blockError
        )
    }

    fun createUserDocInFirestore(user: User, block: (done: Boolean) -> Unit) {
        authenticationRepository.firebaseCreateUserDocInFirestore(user, block)
    }

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
        block: (success: Boolean) -> Unit,
        blockVM: (user: User) -> Unit
    ) {
        authenticationRepository.firebaseLogInWithEmailAndPassword(email, password, {it1->
            if (it1.uid!=null){
                getUserInformation(it1.uid!!){
                    val user = it
                    user.uid = it1.uid!!
                    user.isAuthenticated = true
                    user.isCreated = false
                    blockVM(user)
                }
            }
        }, block)
    }

    fun checkIfLogged(block: (user: User) -> Unit) {
        val uid = authenticationRepository.checkIfLogged()
        if (uid != null) {
            getUserInformation(uid) {
                val user = it
                user.uid = uid
                user.isAuthenticated = true
                block(user)
            }
        }
    }

    fun getUserInformation(uid: String, block: (user: User) -> Unit) {
        authenticationRepository.getUserInformation(uid, block)
    }

    fun signOut(){
        authenticationRepository.firebaseSignOut()
    }
}