package com.example.qhatu.viewmodel

import androidx.lifecycle.ViewModel
import com.example.qhatu.domain.ValidateFormFieldsUseCase

class FormValidatorViewModel : ViewModel() {

    private val validateFormUseCase = ValidateFormFieldsUseCase()

    fun validateForm(email:String, password:String, passwordConfirmed:String, DNI:String) : Boolean{
        return validateFormUseCase.validateRegisterForm(email, password, passwordConfirmed, DNI)
    }

}