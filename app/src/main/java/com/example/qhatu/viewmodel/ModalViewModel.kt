package com.example.qhatu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModalViewModel: ViewModel() {

    enum class ModalState {
        CLOSED,
        REQUEST_MEETING
    }

    private var modalState = MutableLiveData<ModalState>(ModalState.CLOSED)

    fun getModalState(): LiveData<ModalState> {
        return  modalState
    }

    fun setModalState(newState: ModalState) {
        modalState.value = newState
    }

}