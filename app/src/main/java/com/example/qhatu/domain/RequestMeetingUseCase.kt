package com.example.qhatu.domain

import com.example.qhatu.data.FirestoreRepository

class RequestMeetingUseCase {

    val fireStoreRep = FirestoreRepository()

    fun setMeetingDateList() {
        fireStoreRep.getMeetingDatesRef()
            .get()
            .addOnSuccessListener {

            }
    }


}