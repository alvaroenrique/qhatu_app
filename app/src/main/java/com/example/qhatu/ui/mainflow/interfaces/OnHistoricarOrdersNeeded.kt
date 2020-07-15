package com.example.qhatu.ui.mainflow.interfaces

import com.example.qhatu.model.HistoricalOrder

interface OnHistoricalOrdersNeeded {
    fun getHistoricalOrders(block:(success:Boolean)->Unit, blockF:(orderList: ArrayList<HistoricalOrder>) -> Unit)
}