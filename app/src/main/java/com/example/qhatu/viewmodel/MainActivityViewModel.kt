package com.example.qhatu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.ui.model.dao.User

class MainActivityViewModel: ViewModel() {

    // Se setean los casos de uso

    // Se setean los mutable data

    private var user = MutableLiveData<User>()

    // Setters de mutable data
    fun setUser(newUser: User) {
        user.value = newUser
    }

    fun getUser(): LiveData<User> {
        return user
    }

    // Llamada a las funciones de los casos de uso

    // get mutable data : Estas funciones retornan valor cuando el mutable data es cambiado

}
