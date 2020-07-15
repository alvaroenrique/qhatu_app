package com.example.qhatu.ui.mainflow.interfaces

import com.example.qhatu.model.DeliveryDate

interface OnDeliveryDatesNeeded {
    fun getDeliveryDates():ArrayList<DeliveryDate>?
}