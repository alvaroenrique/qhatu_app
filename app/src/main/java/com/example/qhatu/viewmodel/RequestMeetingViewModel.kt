package com.example.qhatu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.model.Meeting
import java.text.SimpleDateFormat
import java.util.*

class RequestMeetingViewModel: ViewModel() {
    val fireStoreRep = FirestoreRepository()

    private var dateMeetings = MutableLiveData<MutableList<Meeting>>()


    fun getDateMeetings(): LiveData<MutableList<Meeting>> {
        return  dateMeetings
    }

    fun setMeetingDateList() {
        var newDateMeetings = mutableListOf<Meeting>()
        fireStoreRep.getMeetingDatesRef()
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val newMeet = document.toObject(Meeting::class.java)
                    newMeet.id = document.id
                    if (newMeet.available) {
                        newDateMeetings.add(newMeet)
                    }
                }
                dateMeetings.value = newDateMeetings
            }
    }

    fun setMeetingById(id: String?, uid: String?, medio: String) {
        if (id != null && uid != null) {
            fireStoreRep.getMeetingDatesRef().document(id).update(mapOf(
                "user" to fireStoreRep.getUserRefById(uid),
                "available" to false,
                "medio" to medio
            ))
        }
    }


}