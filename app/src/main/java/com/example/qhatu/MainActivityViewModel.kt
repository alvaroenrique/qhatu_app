package com.example.qhatu

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun RegistrarUsuario(tipo_doc: String, nro_doc : String, correo : String, pass : String, passConf : String){
        Log.i("quatu", "$tipo_doc $nro_doc $correo $pass $passConf")
    }

}
