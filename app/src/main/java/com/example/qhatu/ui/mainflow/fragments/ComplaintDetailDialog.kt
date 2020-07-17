package com.example.qhatu.ui.mainflow.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.custom_complaint_detail_dialog.*

class ComplaintDetailDialog: DialogFragment() {

    private lateinit var model: MainActivityViewModel

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
        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)

        butComplaintDetail.setOnClickListener {
            val fragmentManager =  requireActivity().supportFragmentManager
            val newFragment = ComplaintConfirmationDialog()
            newFragment.show(fragmentManager, "dialog")

            model.setCurrentComplaint(eteComplaintDetail.text.toString())

            dialog?.dismiss()
        }
    }

}