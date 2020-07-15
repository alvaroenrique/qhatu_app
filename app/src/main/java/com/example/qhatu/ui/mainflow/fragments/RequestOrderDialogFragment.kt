package com.example.qhatu.ui.mainflow.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.qhatu.R
import com.example.qhatu.model.DeliveryDate
import com.example.qhatu.ui.mainflow.interfaces.OnDeliveryDatesNeeded
import com.example.qhatu.ui.mainflow.interfaces.OnPlaceOrder
import kotlinx.android.synthetic.main.custom_request_order_dialog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RequestOrderDialogFragment : DialogFragment() {

    private var onDeliveryDatesNeededListener: OnDeliveryDatesNeeded? = null
    private var onPlaceOrderListener: OnPlaceOrder? = null

    private var deliveryDatesArrayList: ArrayList<DeliveryDate>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onDeliveryDatesNeededListener = activity as OnDeliveryDatesNeeded
        onPlaceOrderListener = activity as OnPlaceOrder
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_request_order_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deliveryDatesArrayList = onDeliveryDatesNeededListener?.getDeliveryDates()
        val deliveryDateSpinnerValues = arrayListOf<String>()
        deliveryDatesArrayList!!.forEach {
            val date = it.deliverDateInfo.date.toDate()
            val locale = Locale("es", "PE")
            val format = SimpleDateFormat("EEEE d 'de' MMMM", locale)
            deliveryDateSpinnerValues.add(format.format(date).capitalize())
        }

        val adapter = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            deliveryDateSpinnerValues
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiDeliverDate.adapter = adapter

        butOrder.setOnClickListener {
            onPlaceOrderListener!!.placeOrder(
                deliveryDatesArrayList!![spiDeliverDate.selectedItemPosition],
                spiPaymentMethod.selectedItem.toString(),
                spiMarket.selectedItem.toString()
            ) {
                if (it) {
                    Toast.makeText(activity, "Done", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Done", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
