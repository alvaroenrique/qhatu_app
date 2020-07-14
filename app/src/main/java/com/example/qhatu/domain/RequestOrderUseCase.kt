package com.example.qhatu.domain

import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.model.DeliveryDate

class RequestOrderUseCase {

    private val firestoreRepository = FirestoreRepository()

    fun getAvailableDeliveryDates(
        block: (availableDeliveryDates: ArrayList<DeliveryDate>) -> Unit,
        blockError: (error: String) -> Unit
    ) {
        firestoreRepository.getDeliverDatesAvailable(block, blockError)
    }

}