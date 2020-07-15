package com.example.qhatu.ui.mainflow.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.qhatu.R
import com.example.qhatu.data.FirestoreRepository
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.Meeting
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.example.qhatu.viewmodel.RequestMeetingViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_product_dialog.*
import kotlinx.android.synthetic.main.fragment_request_meeting.*
import java.text.SimpleDateFormat
import java.util.*


class AddProductDialogFragment : DialogFragment() {

    private lateinit var model: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.fragment_add_product_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)

        butOrderDialog.setOnClickListener {

            val firestore = FirestoreRepository()
            val currentUid = model.getUid().value
            if (currentUid != null) {
                firestore.getUserRefById(currentUid).collection("purchase-list")
                    .add(hashMapOf(
                        "detail" to eteDetail.text.toString(),
                        "measurement-quantity" to  eteMesurementQuantity.text?.toString()?.toInt(),
                        "measurement-unit" to eteMeasurementUnit.selectedItem,
                        "product-category" to firestore.getProductCategoryRef(model.getCurrentProductId().value),
                        "product-name" to eteProductName.text.toString(),
                        "quantity" to eteMesurementQuantity.text?.toString()?.toInt()
                    ))
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Producto agregado", Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }


                val activty_instance = activity as MainActivity
                val curr = model.getCurrentProductId().value
                if (curr != null) {
                    activty_instance.ListasProductos(curr)
                }

            }

        }


    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


        return dialog
    }

}