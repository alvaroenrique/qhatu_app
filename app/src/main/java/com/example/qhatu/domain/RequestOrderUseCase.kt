package com.example.qhatu.domain

import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.model.DeliveryDate
import com.example.qhatu.ui.model.User

class RequestOrderUseCase {

    private val firestoreRepository = FirestoreRepository()

    fun getAvailableDeliveryDates(
        block: (availableDeliveryDates: ArrayList<DeliveryDate>) -> Unit,
        blockError: (error: String) -> Unit
    ) {
        firestoreRepository.getDeliverDatesAvailable(block, blockError)
    }

    fun placeOrder(
        deliveryDate: DeliveryDate,
        paymentMethod: String,
        superMarket: String,
        block: (success: Boolean) -> Unit,
        user: User
    ) {
        firestoreRepository.placeOrder(deliveryDate, paymentMethod, superMarket, block, user)
    }

}