package com.example.qhatu.domain

import com.example.qhatu.data.RegisterValidatorRepository
import com.example.qhatu.ui.model.User

class ValidateNewUserUseCase {

    private val registerValidatorRepository = RegisterValidatorRepository()

    fun validateExistenceInActiveCustomers(
        mail: String,
        DNI: String,
        block: (found: Boolean) -> Unit,
        blockVM: (user: User) -> Unit
    ) {
        registerValidatorRepository.firebaseValidateExistenceInActiveCustomers(
            DNI,
            mail,
            block,
            blockVM
        )
    }

}