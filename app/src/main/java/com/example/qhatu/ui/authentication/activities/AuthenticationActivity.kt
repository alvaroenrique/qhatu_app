package com.example.qhatu.ui.authentication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.qhatu.R
import com.example.qhatu.ui.authentication.fragments.LoginFragment
import com.example.qhatu.ui.authentication.fragments.RegisterFragment

class AuthenticationActivity : AppCompatActivity() {

    private var fraLogin : Fragment? = null
    private var fraRegister : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        fraLogin = LoginFragment()
        fraRegister =
            RegisterFragment()

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.falaAuthentication, fraLogin!!)
        ft.commit()
    }
}