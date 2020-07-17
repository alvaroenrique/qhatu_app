package com.example.qhatu.ui.mainflow.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.R
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.custom_complaint_confirmation_dialog.*

class ComplaintConfirmationDialog: DialogFragment() {

    private lateinit var model: MainActivityViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_complaint_confirmation_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)

        butCancel.setOnClickListener {
            dialog?.dismiss()
        }

        butComplaint.setOnClickListener {
            setComplaint()
        }
    }

    fun setComplaint() {
        FirestoreRepository().db.collection("workers")
            .document(model.orderDuoId)
            .collection("complaints")
            .add(
                hashMapOf(
                    "complaint" to model.getCurrentComplaint().value
                )
            ).addOnSuccessListener {
                val fragmentManager =  requireActivity().supportFragmentManager
                val newFragment = ComplaintSuccessDialog()
                newFragment.show(fragmentManager, "dialog")
                dialog?.dismiss()
            }.addOnFailureListener {
                dialog?.dismiss()
            }

    }

}