package com.example.qhatu.ui.model.dao

import com.google.firebase.Timestamp
import java.util.*

data class User(
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