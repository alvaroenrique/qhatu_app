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

data class Meeting(
    var available: Boolean = false,
    var date: Timestamp = Timestamp(
        Date()
    ),
    var id: String = ""
)

data class Worker(
    var apellido1: String = "",
    var apellido2: String = "",
    var celular1: Long = 0,
    var celular2: Long = 0,
    var modeloCarro: String = "",
    var nombre1: String = "",
    var nombre2: String = "",
    var placaCarro: String = ""
)

data class Order(
    var date: Timestamp = Timestamp(
        Date()
    ),
    var ticketPurchase: String = "",
    var cost: Long = 0,
    var state: String = "",
    var supermarket: String = "",
    var dateReference: DocumentReference? = null
)