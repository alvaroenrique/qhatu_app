package com.example.qhatu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.domain.HistoricalOrdersUseCase
import com.example.qhatu.model.HistoricalOrder

class HistoricalOrdersViewModel : ViewModel() {

    val historicalOrdersUseCase = HistoricalOrdersUseCase()

    val historicarOrdersData = MutableLiveData<ArrayList<HistoricalOrder>>()

    fun setHistoricarOrdersData(orderList:ArrayList<HistoricalOrder>){
        historicarOrdersData.value = orderList
    }

    fun getHistoricalOrdersData(uid:String, block:(success:Boolean)->Unit, blockF:(orderList: ArrayList<HistoricalOrder>) -> Unit){
        historicalOrdersUseCase.getHistoricalOrders(uid, block, blockF){
            setHistoricarOrdersData(it)
        }
    }

    fun getHistoricalOrdersLiveData():LiveData<ArrayList<HistoricalOrder>>{
        return historicarOrdersData
    }

}