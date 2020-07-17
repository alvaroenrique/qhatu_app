package com.example.qhatu.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.ui.model.Order
import com.example.qhatu.ui.model.UserInfo
import com.example.qhatu.ui.model.Worker


class MainActivityViewModel: ViewModel() {

    // Variables temporales
    var orderDuoId = "SWyGnLS688xzNm1MiDLa"

    // Se setean los casos de uso

    // Se setean los mutable data

    private var user = MutableLiveData<UserInfo>()

    private var userPicture = MutableLiveData<Uri>()

    private var uid = MutableLiveData<String>()

    private var addIconVisible = MutableLiveData<Boolean>()

    private var currentProductId = MutableLiveData<String>()

    private var currentWorker = MutableLiveData<Worker>()

    private var currentComplaint = MutableLiveData<String>()

    private var currentOrder = MutableLiveData<Order>()

    // Setters de mutable data
    fun setUser(newUser: UserInfo) {
        user.value = newUser
    }

    fun getUser(): LiveData<UserInfo> {
        return user
    }

    fun setUserPicture(newUserPicture: Uri) {
        userPicture.value = newUserPicture
    }

    fun getUserPicture(): LiveData<Uri> {
        return userPicture
    }

    fun getUid(): MutableLiveData<String> {
        return uid
    }
    fun setUid(newUid:String?) {
        uid.value = newUid
    }

    fun getAddIconVisible(): MutableLiveData<Boolean> {
        return addIconVisible
    }
    fun setAddIconVisible(newState: Boolean) {
        addIconVisible.value = newState
    }

    fun getCurrentProductId(): MutableLiveData<String> {
        return currentProductId
    }
    fun setCurrentProductId(newProductId: String) {
        currentProductId.value = newProductId
    }

    fun getCurrentWorker(): MutableLiveData<Worker> {
        return currentWorker
    }
    fun setCurrentWorker(newWorker: Worker) {
        currentWorker.value = newWorker
    }

    fun getCurrentComplaint(): MutableLiveData<String> {
        return currentComplaint
    }
    fun setCurrentComplaint(newComplaint: String) {
        currentComplaint.value = newComplaint
    }

    fun getCurrentOrder(): MutableLiveData<Order> {
        return currentOrder
    }
    fun setCurrentOrder(newOrder: Order) {
        currentOrder.value = newOrder
    }

    // Llamada a las funciones de los casos de uso

    // get mutable data : Estas funciones retornan valor cuando el mutable data es cambiado

}
