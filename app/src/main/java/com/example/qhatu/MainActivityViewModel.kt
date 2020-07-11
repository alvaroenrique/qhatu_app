package com.example.qhatu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

}
