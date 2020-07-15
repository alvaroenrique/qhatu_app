package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.viewmodel.MainActivityViewModel

class PurchaseListProductDetailFragment : Fragment() {
    private var teviPLProdDetailTitle : TextView? = null
    private var teviDetail : TextView? = null

    private lateinit var model: MainActivityViewModel

    val args :PurchaseListProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_list_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)
        model.setAddIconVisible(false)

        val nombre_producto = args.nombre
        val detalle_producto = args.detalle

        teviPLProdDetailTitle = view.findViewById(R.id.teviPLProdDetailTitle)
        teviDetail = view.findViewById(R.id.teviDetail)

        teviPLProdDetailTitle?.text = "Detalle de $nombre_producto"
        teviDetail?.text = detalle_producto
    }
}