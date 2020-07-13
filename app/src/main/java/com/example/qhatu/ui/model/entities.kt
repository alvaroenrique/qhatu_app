package com.example.qhatu.ui.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import java.util.*

data class User(
    var uid: String? = null,
    var email: String? = null,
    var isAuthenticated: Boolean = false,
    var userInfo : UserInfo? = null,
    var userReference : String? = null,
    var userDocumentId : String? = null
)

data class UserInfo(
    val DNI: Long = 0,
    val apellidoMat: String = "",
    val apellidoPat: String = "",
    val apellidos: String = "",
    val celular: Long = 0,
    val domicilio: String = "",
    val domicilio1: String = "",
    val fechaNacimiento: Timestamp = Timestamp(
        Date()
    ),
    val mail: String = "",
    val nombre: String = "",
    val referencia: String = ""
)