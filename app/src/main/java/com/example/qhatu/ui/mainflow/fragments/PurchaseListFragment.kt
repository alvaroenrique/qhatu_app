package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.viewmodel.ModalViewModel
import kotlinx.android.synthetic.main.fragment_purchase_list.*


class PurchaseListFragment : Fragment() {

    lateinit var modalViewmodel: ModalViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modalViewmodel = ViewModelProvider((activity as MainActivity)).get(ModalViewModel::class.java)

        butMeetingReq.setOnClickListener {
            modalViewmodel.setModalState(ModalViewModel.ModalState.REQUEST_MEETING)
        }

    }


}