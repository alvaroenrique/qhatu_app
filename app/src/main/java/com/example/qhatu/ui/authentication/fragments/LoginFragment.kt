package com.example.qhatu.ui.authentication.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.qhatu.R
import com.example.qhatu.ui.authentication.interfaces.OnChangeAuthenticationMode

class LoginFragment : Fragment() {

    //Listeners
    private var changeAUthenticationModeListener : OnChangeAuthenticationMode? = null

    //View Elements
    private var butRegister : Button? = null
    private var butLogIn : Button? = null

    private var eteMail : EditText? = null
    private var etePassword : EditText? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeAUthenticationModeListener = activity as OnChangeAuthenticationMode
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        butRegister = view?.findViewById(R.id.butRegistrarse)
        butLogIn = view?.findViewById(R.id.butIniciarSesion)

        eteMail = view?.findViewById(R.id.eteEmail)
        etePassword = view?.findViewById(R.id.etePass)

        butRegister?.setOnClickListener {
            changeAUthenticationModeListener?.changeAuthenticationMode("register")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}