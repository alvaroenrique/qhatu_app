package com.example.qhatu.ui.authentication.interfaces

interface OnLogin {
    fun logInWithEmailAndPassword(
        email: String,
        password: String,
        block: (success: Boolean) -> Unit
    )
}