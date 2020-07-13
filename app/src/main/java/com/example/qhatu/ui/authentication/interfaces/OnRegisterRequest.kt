package com.example.qhatu.ui.authentication.interfaces

import com.example.qhatu.ui.model.User

interface OnRegisterRequest {
    fun validateRegisterFields(
        email: String,
        password: String,
        passwordConf: String,
        DNI: String
    ): Boolean
    fun validateUserInActiveCustomers(email: String, DNI: String, block: (found: Boolean) -> Unit)
    fun registerUser(email:String, password:String, block:(success:Boolean)->Unit)
}