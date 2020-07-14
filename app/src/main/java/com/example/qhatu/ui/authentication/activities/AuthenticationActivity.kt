package com.example.qhatu.ui.authentication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.qhatu.R
import com.example.qhatu.ui.authentication.fragments.LoginFragment
import com.example.qhatu.ui.authentication.fragments.RegisterFragment
import com.example.qhatu.ui.authentication.interfaces.OnChangeAuthenticationMode
import com.example.qhatu.ui.authentication.interfaces.OnLogin
import com.example.qhatu.ui.authentication.interfaces.OnRegisterRequest
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.User
import com.example.qhatu.viewmodel.AuthenticationViewModel
import com.example.qhatu.viewmodel.FormValidatorViewModel

class AuthenticationActivity : AppCompatActivity(), OnChangeAuthenticationMode, OnRegisterRequest,
    OnLogin {

    private var formValidatorViewModel: FormValidatorViewModel? = null
    private var authenticationViewModel: AuthenticationViewModel? = null

    private var fraLogin: Fragment? = null
    private var fraRegister: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        setUpFormValidatorViewModel()
        setUpAuthenticationViewModel()
        authenticationViewModel!!.checkIfLogged()

        fraLogin = LoginFragment()

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.falaAuthentication, fraLogin!!)
        ft.commit()
    }

    fun setUpFormValidatorViewModel() {
        formValidatorViewModel = FormValidatorViewModel()
    }

    fun setUpAuthenticationViewModel() {
        authenticationViewModel = AuthenticationViewModel()
        val authenticationObserver = Observer<User> {
            if(it.isCreated){
                createUserDocInFirestore {
                    if (it) {
                        Log.d("AuthenticationLog::", "Created in firestore")
                    } else {
                        TODO("Hacer algo para que se cree despues")
                    }
                }
            }
            if (it.isAuthenticated) {
                Log.d("AuthenticationLog::", "Ya podemos ir a loggearnos")
                goToMainActivity()
            } else {
                Log.d("AuthenticationLog::", "Not yet authenticated")
            }
        }

        authenticationViewModel?.getUserLiveData()?.observe(this, authenticationObserver)
    }

    private fun goToMainActivity() {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivityForResult(intent, 1000)
        this.finish()
    }

    override fun changeAuthenticationMode(mode: String) {
        fraRegister = RegisterFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.falaAuthentication, fraRegister!!)
        ft.commit()
    }

    override fun validateRegisterFields(
        email: String,
        password: String,
        passwordConf: String,
        DNI: String
    ): Boolean {
        // Validando los campos de formulario
        if (formValidatorViewModel!!.validateForm(email, password, passwordConf, DNI)) {
            Log.d("FormValidatorLog::", "Approved")
            return true
        } else {
            Log.d("FormValidatorLog::", "Disapproved")
            return false
        }
    }

    override fun validateUserInActiveCustomers(
        email: String,
        DNI: String,
        block: (found: Boolean) -> Unit
    ) {
        authenticationViewModel!!.validateExistenceInActiveCustomers(DNI, email, block)
    }

    override fun registerUser(email: String, password: String, block: (success: Boolean) -> Unit) {
        authenticationViewModel!!.createUserWithEmailAndPassword(email, password, block)
    }

    fun createUserDocInFirestore(block: (done: Boolean) -> Unit) {
        authenticationViewModel!!.createUserDocInFirestore(block)
    }

    override fun logInWithEmailAndPassword(
        email: String,
        password: String,
        block: (success: Boolean) -> Unit
    ) {
        authenticationViewModel!!.logInWithEmailAndPassword(email, password, block)
    }
}