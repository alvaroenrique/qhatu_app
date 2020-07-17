package com.example.qhatu.ui.mainflow.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.qhatu.R
import kotlinx.android.synthetic.main.custom_complaint_detail_dialog.*

class ComplaintDetailDialog: DialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_complaint_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        butComplaintDetail.setOnClickListener {
            val fragmentManager =  requireActivity().supportFragmentManager
            val newFragment = ComplaintConfirmationDialog()
            newFragment.show(fragmentManager, "dialog")
            dialog?.dismiss()
        }
    }

}