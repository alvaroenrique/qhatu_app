package com.example.qhatu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.domain.AuthenticationUseCase
import com.example.qhatu.domain.PersistenceUseCase
import com.example.qhatu.domain.ValidateNewUserUseCase
import com.example.qhatu.ui.model.User


class AuthenticationViewModel : ViewModel() {

    // Setear use cases
    val validateNewUserUseCase = ValidateNewUserUseCase()
    val authenticationUseCase = AuthenticationUseCase()
    val persistenceUseCase = PersistenceUseCase()

    // Setear Mutable Live Data
    companion object Factory {
        private val userLiveData = MutableLiveData<User>()
    }

    // Setear value setters para los live data
    fun setUser(user: User) {
        userLiveData.value = user
    }

    // Call to Use Cases
    fun validateExistenceInActiveCustomers(
        DNI: String,
        mail: String,
        block: (found: Boolean) -> Unit
    ) {
        validateNewUserUseCase.validateExistenceInActiveCustomers(mail, DNI, block) {
            setUser(it)
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        block: (success: Boolean) -> Unit
    ) {
        authenticationUseCase.createUserAndSetObject(email, password, userLiveData.value!!, {
            setUser(it)
            block(true)
        }, {
            block(false)
        })
    }

    fun createUserDocInFirestore(block: (done: Boolean) -> Unit) {
        authenticationUseCase.createUserDocInFirestore(userLiveData.value!!, block)
    }

    fun logInWithEmailAndPassword(
        email: String,
        password: String,
        block: (success: Boolean) -> Unit
    ) {
        authenticationUseCase.loginWithEmailAndPassword(email, password, block) {
            setUser(it)
            Log.d("LoginLOG::", it.toString())
        }
    }

    fun checkIfLogged(){
        authenticationUseCase.checkIfLogged {
            setUser(it)
            Log.d("CheckedLOG::", it.toString())
        }
    }

    // Reaccionar al cambio
    fun getUserLiveData(): LiveData<User> {
        return userLiveData
    }

}