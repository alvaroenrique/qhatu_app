package com.example.qhatu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.model.Meeting
import java.text.SimpleDateFormat
import java.util.*

class RequestMeetingViewModel: ViewModel() {
    val fireStoreRep = FirestoreRepository()

    private var dateMeetings = MutableLiveData<MutableList<String>>()


    fun getDateMeetings(): LiveData<MutableList<String>> {
        return  dateMeetings
    }

    fun setMeetingDateList() {
        var newDateMeetings = mutableListOf<String>()
        fireStoreRep.getMeetingDatesRef()
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val newMeet = document.toObject(Meeting::class.java)
                    val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    newDateMeetings.add(sfd.format(newMeet.date.toDate().time - 3600 * 5000))
                }
                dateMeetings.value = newDateMeetings
            }
    }
}