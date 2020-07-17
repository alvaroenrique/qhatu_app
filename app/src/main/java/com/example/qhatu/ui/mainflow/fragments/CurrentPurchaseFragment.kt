package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.qhatu.R
import kotlinx.android.synthetic.main.fragment_current_purchase.*


class CurrentPurchaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        relaDeliverDuo.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_currentPurchaseFragment_to_orderDuoFragment)
        }
    }

}