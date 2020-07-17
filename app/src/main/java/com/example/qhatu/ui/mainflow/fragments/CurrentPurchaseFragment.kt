package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.qhatu.R
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.Order
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_current_purchase.*
import java.text.SimpleDateFormat
import java.util.*


class CurrentPurchaseFragment : Fragment() {

    private lateinit var model: MainActivityViewModel

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
        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)

        relaDeliverDuo.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_currentPurchaseFragment_to_orderDuoFragment)
        }

        relaPurchareList.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_currentPurchaseFragment_to_purchaseListFragment)
        }

        setOrder()


        model.getCurrentOrder().observe(viewLifecycleOwner ,Observer<Order> { newOrder ->
            teviOrderDetailServiceCost.text = "s/.  ${newOrder.cost.toString()}"
            teviOrderDetailStatus.text = newOrder.state
            teviOrderDetailSuperMarket.text = newOrder.supermarket
            val locale = Locale("es", "PE")
            val format = SimpleDateFormat("EEEE d 'de' MMMM", locale)
            teviOrderDetailDeliveryDate.text =format.format(newOrder.date.toDate()).capitalize()
        })


    }

    fun setOrder() {
        val uid = model.getUid().value
        if (uid != null) {
            FirestoreRepository().getUserRefById(uid)
                .collection("orders")
                .orderBy("date", Query.Direction.DESCENDING).limit(1)
                .get().addOnSuccessListener { result ->
                    val newOrder = result.documents[0].toObject(Order::class.java)
                    if (newOrder != null) {

                        newOrder.dateReference?.get()?.addOnSuccessListener {

                            newOrder.date = it.data?.get("date") as Timestamp
                            model.setCurrentOrder(newOrder)
                        }
                    }
                }

        }

    }

}