package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
//import androidx.lifecycle.ViewModelProviders
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.viewmodel.ModalViewModel
import kotlinx.android.synthetic.main.fragment_purchase_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseListFragment : Fragment() {
    private var mlistarCategorias : ListView? = null
    lateinit var modalViewmodel: ModalViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        val activity_instance = activity as MainActivity

        mlistarCategorias = view.findViewById(R.id.liviPL)

        activity_instance.ListarCategorias()

        mlistarCategorias?.setOnItemClickListener() { parent : AdapterView<*>, view:View, position:Int, id:Long ->

            val data = parent.adapter.getItem(position).toString()
            val yourArray: List<String> = data.split(",")

            val nombre = yourArray[0]
            val id = yourArray[1]

            val action = PurchaseListFragmentDirections.actionPurchaseListFragmentToPurchaseListProductFragment(nombre,id)
            Navigation.findNavController(view).navigate(action)

        }

        /*val model = ViewModelProviders.of((activity as MainActivity)).get(MainActivityViewModel::class.java)

        model.isLogged.value = true*/

        modalViewmodel = ViewModelProvider((activity as MainActivity)).get(ModalViewModel::class.java)

        butMeetingReq.setOnClickListener {
            modalViewmodel.setModalState(ModalViewModel.ModalState.REQUEST_MEETING)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurchaseListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}