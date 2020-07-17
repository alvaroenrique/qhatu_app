package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.R
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.Worker
import com.example.qhatu.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_order_duo.*


class OrderDuoFragment: Fragment()  {

    private lateinit var model: MainActivityViewModel

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
        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)

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

        setOrderDuo()

        model.getCurrentWorker().observe(viewLifecycleOwner, Observer<Worker> { newWorker ->
            teviDuoName.text = "${newWorker.nombre1} ${newWorker.apellido1} y ${newWorker.nombre2} ${newWorker.apellido2}"
            teviDuoPhones.text = "${newWorker.celular1} - ${newWorker.celular2}"
            teviDuoCarModel.text = "${newWorker.modeloCarro}"
            teviDuoCarPlate.text = "${newWorker.placaCarro}"
        })

    }

    fun setOrderDuo(){
        FirestoreRepository().db.collection("workers")
            .document(model.orderDuoId)
            .get().addOnSuccessListener { res ->
                val newWorker = res.toObject(Worker::class.java)
                if (newWorker != null) {
                    model.setCurrentWorker(newWorker)
                }
            }
    }

}