package com.example.qhatu.ui.mainflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.qhatu.R
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.dao.User
import com.example.qhatu.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePurchaseListLink.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_purchaseListFragment)
        }

        val model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)
        val currentUser = model.getUser().value

        profileNameInput.setText(currentUser?.nombre)
        profileLastNameInput.setText(currentUser?.apellidos)
        profilePhoneInput.setText("${currentUser?.celular ?: ""}")
        profileEmailInput.setText(currentUser?.mail)
        profileDirectionField.text = currentUser?.domicilio

        val nameObserver = Observer<User> { newName ->
            profileNameInput.setText(newName.nombre)
            profileLastNameInput.setText(newName.apellidos)
            profilePhoneInput.setText("${newName.celular}")
            profileEmailInput.setText(newName.mail)
            profileDirectionField.text = newName.domicilio
        }
        model.getUser().observe(viewLifecycleOwner, nameObserver)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}