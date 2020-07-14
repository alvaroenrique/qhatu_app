package com.example.qhatu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.qhatu.domain.RequestOrderUseCase
import com.example.qhatu.model.DeliveryDate

class RequestOrderViewModel {

    val requestOrderUseCase = RequestOrderUseCase()

    val availableDeliveryDateArrayData = MutableLiveData<ArrayList<DeliveryDate>>()

    fun setAvailableDeliveryDateArrayData(availableDeliveryDateArray: ArrayList<DeliveryDate>){
        availableDeliveryDateArrayData.value = availableDeliveryDateArray
    }

    fun getAvailableDeliveryDateArray(blockError:(error:String)->Unit){
        requestOrderUseCase.getAvailableDeliveryDates({
            setAvailableDeliveryDateArrayData(it)
        },blockError)
    }

    fun getAvailableDeliveryDateArrayDataLiveData() : LiveData<ArrayList<DeliveryDate>>{
        return availableDeliveryDateArrayData
    }

}