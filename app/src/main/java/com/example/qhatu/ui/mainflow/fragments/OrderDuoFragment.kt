package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.qhatu.R
import kotlinx.android.synthetic.main.fragment_order_duo.*


class OrderDuoFragment: Fragment()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_duo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        butOrderRate.setOnClickListener {
            val fragmentManager =  requireActivity().supportFragmentManager
            val newFragment = RateServiceDialog()
            newFragment.show(fragmentManager, "dialog")
        }

        butOrderComplaint.setOnClickListener {
            val fragmentManager =  requireActivity().supportFragmentManager
            val newFragment = ComplaintDetailDialog()
            newFragment.show(fragmentManager, "dialog")
        }

    }
}