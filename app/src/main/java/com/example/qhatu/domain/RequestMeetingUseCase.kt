package com.example.qhatu.domain

import android.util.Log
import com.example.qhatu.data.FirestoreRepository

class RequestMeetingUseCase {

    val fireStoreRep = FirestoreRepository()

    fun setMeetingDateList() {
        fireStoreRep.getMeetingDatesRef()
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("asdasdasdasd", "${document.id} => ${document.data}")
                }
            }
    }


}