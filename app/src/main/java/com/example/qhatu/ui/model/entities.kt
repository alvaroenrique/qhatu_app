package com.example.qhatu.ui.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import java.util.*

data class User(
    var uid: String? = null,
    var email: String? = null,
    var isAuthenticated: Boolean = false,
    var isCreated: Boolean = false,
    var userInfo : UserInfo? = null,
    var userReference : DocumentReference? = null,
    var userDocumentId : String? = null
)

data class UserInfo(
    val DNI: Long = 0,
    val apellidoMat: String = "",
    val apellidoPat: String = "",
    var apellidos: String = "",
    var celular: Long = 0,
    val domicilio: String = "",
    val domicilio1: String = "",
    val fechaNacimiento: Timestamp = Timestamp(
        Date()
    ),
    var mail: String = "",
    var nombre: String = "",
    val referencia: String = ""
)