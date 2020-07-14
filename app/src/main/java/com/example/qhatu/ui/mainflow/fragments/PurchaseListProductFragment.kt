package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity

class PurchaseListProductFragment : Fragment(){
    private var teviPLProdTitle : TextView? = null
    private var mListarProductos : ListView? = null

    val args :PurchaseListProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_list_product, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Cambio el título del fragment segun la categoria clickeada
        val id_categoria = args.id
        val nombre_categoria = args.nombre
        teviPLProdTitle = view.findViewById(R.id.teviPLProdTitle)
        teviPLProdTitle?.text = nombre_categoria

        val activty_instance = activity as MainActivity

        mListarProductos = view.findViewById(R.id.liviPLProduct)

        activty_instance.ListasProductos(id_categoria)

        mListarProductos?.setOnItemClickListener(){ parent : AdapterView<*>, view:View, position:Int, id:Long ->
            val data = parent.adapter.getItem(position).toString()
            val yourArray: List<String> = data.split(",")

            val detalle_prod = yourArray[1]
            val nombre_prod = yourArray[0]

            val action = PurchaseListProductFragmentDirections.actionPurchaseListProductFragmentToPurchaseListProductDetailFragment(nombre_prod, detalle_prod)
            Navigation.findNavController(view).navigate(action)

        }


    }
}