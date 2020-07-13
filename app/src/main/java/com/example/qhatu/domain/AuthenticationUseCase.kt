package com.example.qhatu.domain

import com.example.qhatu.data.AuthenticationRepository
import com.example.qhatu.ui.model.User

class AuthenticationUseCase {

    // Se inicializa el repositorio a utilizar
    val authenticationRepository = AuthenticationRepository()

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

    fun createUserDocInFirestore(user:User, block:(done:Boolean)->Unit){
        authenticationRepository.firebaseCreateUserDocInFirestore(user, block)
    }
}