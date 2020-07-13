package com.example.qhatu.ui.model.dao

import com.google.firebase.Timestamp
import java.util.*

data class User(
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