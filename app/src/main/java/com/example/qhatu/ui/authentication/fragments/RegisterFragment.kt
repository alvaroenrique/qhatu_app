package com.example.qhatu.ui.authentication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qhatu.R
import com.example.qhatu.ui.authentication.interfaces.OnChangeAuthenticationMode
import com.example.qhatu.ui.authentication.interfaces.OnRegisterRequest

class RegisterFragment : Fragment() {

    // Listener
    private var onRegisterRequestListener: OnRegisterRequest? = null

    // View Elements
    private var butRegister: Button? = null

    private var eteNroDoc: EditText? = null
    private var eteMail: EditText? = null
    private var etePassword: EditText? = null
    private var eteConfirmPassword: EditText? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRegisterRequestListener = activity as OnRegisterRequest
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        butRegister = view?.findViewById(R.id.butRegisterUser)
        eteNroDoc = view?.findViewById(R.id.eteNroDoc)
        eteMail = view?.findViewById(R.id.eteMailRegister)
        etePassword = view?.findViewById(R.id.eteContra)
        eteConfirmPassword = view?.findViewById(R.id.eteContraConf)

        butRegister?.setOnClickListener {
            val formOk = onRegisterRequestListener!!.validateRegisterFields(
                eteMail!!.text.toString(),
                etePassword!!.text.toString(),
                eteConfirmPassword!!.text.toString(),
                eteNroDoc!!.text.toString()
            )

            if (formOk) {
                onRegisterRequestListener!!.validateUserInActiveCustomers(
                    eteMail!!.text.toString(),
                    eteNroDoc!!.text.toString()
                ) { it1 ->
                    if (it1) {
                        Log.d("RegisterLog::", "Si existe usuario y se puede registrar")
                        onRegisterRequestListener!!.registerUser(
                            eteMail!!.text.toString(),
                            etePassword!!.text.toString()
                        ) {
                            if (it) {
                                Toast.makeText(activity, "Registrado y logeado", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Ocurrio un error en el registro y login",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Log.d("RegisterLog::", "No existe usuario y no puede registrar")
                    }
                }
            } else {
                Log.d("RegisterLog::", "Formulario de registro no validado")
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

}