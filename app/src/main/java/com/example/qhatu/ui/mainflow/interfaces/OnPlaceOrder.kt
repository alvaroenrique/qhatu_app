package com.example.qhatu.ui.mainflow.interfaces

import com.example.qhatu.model.DeliveryDate
import com.google.firebase.firestore.auth.User

interface OnPlaceOrder {
    fun placeOrder(
        deliveryDate: DeliveryDate,
        paymentMethod: String,
        superMarket: String,
        block: (success: Boolean) -> Unit
    )
}