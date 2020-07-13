package com.example.qhatu.domain

import android.util.Log

class ValidateFormFieldsUseCase {

    // Declaracion de metodos para validar valores de campos

    // Validacion de contrasenas iguales
    fun comparePasswordFields(password: String, passwordConfirmed: String): Boolean {

        if (password == passwordConfirmed) {
            Log.d("FormFieldValidateLog::", "Contrasenas Iguales")
            return true
        } else {
            Log.d("FormFieldValidateLog::", "Contrasenas diferentes")
            return false
        }
    }

    // Validar formato del correo
    fun validateMailFormat(email: String): Boolean {
        // General email regex (RFC 5322 Official Standard)
        val emailPattern =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        val emailRegex = emailPattern.toRegex()

        if (email.matches(emailRegex)) {
            Log.d("FormFieldValidateLog::", "Formato valido del mail")
            return true
        } else {
            Log.d("FormFieldValidateLog::", "Formato invalido del mail")
            return false
        }
    }

    // Validar formato de DNI
    fun validateDNIFormat(DNI: String): Boolean {
        if (DNI.length == 8) {
            val dniPattern = "([0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])"
            val dniRegex = dniPattern.toRegex()
            if (DNI.matches(dniRegex)) {
                Log.d("FormFieldValidateLog::", "DNI valido")
                return true
            } else {
                Log.d("FormFieldValidateLog::", "DNI Solo usa numeros")
                return false
            }
        } else {
            Log.d("FormFieldValidateLog::", "DNI no aceptado")
            return false
        }
    }

    fun validateRegisterForm(
        email: String,
        password: String,
        passwordConfirmed: String,
        DNI: String
    ) : Boolean{
        if (comparePasswordFields(password,passwordConfirmed)&&validateMailFormat(email)&&validateDNIFormat(DNI)){
            Log.d("RegisterFormLog::", "Puede registrarse")
            return true
        } else{
            Log.d("RegisterFormLog::", "No puede registrarse")
            return false
        }
    }

}