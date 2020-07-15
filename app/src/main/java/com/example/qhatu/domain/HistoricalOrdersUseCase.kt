package com.example.qhatu.domain

import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.model.HistoricalOrder

class HistoricalOrdersUseCase {

    val firestoreRepository = FirestoreRepository()

    fun getHistoricalOrders(
        uid: String,
        block: (success: Boolean) -> Unit,
        blockF: (orderList: ArrayList<HistoricalOrder>) -> Unit,
        blockVM:(orderList: ArrayList<HistoricalOrder>) -> Unit
    ) {
        firestoreRepository.getHistoricalOrders(uid, block, blockF, blockVM)
    }

}