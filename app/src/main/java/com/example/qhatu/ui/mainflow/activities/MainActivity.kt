package com.example.qhatu.ui.mainflow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.qhatu.viewmodel.MainActivityViewModel
import com.example.qhatu.R
import com.example.qhatu.domain.ProfileUseCase
import com.example.qhatu.ui.mainflow.fragments.RequestMeetingFragment
import com.example.qhatu.ui.model.UserInfo
import com.example.qhatu.viewmodel.ModalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var listener: NavController.OnDestinationChangedListener

    private lateinit var mainActivityViewmodel: MainActivityViewModel
    private lateinit var modalViewmodel: ModalViewModel


    private lateinit var profileUserCase: ProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView.setupWithNavController(navController)

        navigationView.itemIconTintList = null

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment,
                R.id.currentPurchaseFragment,
                R.id.purchaseHistoryFragment,
                R.id.purchaseListFragment
            ), drawerLayout
        )

        mainActivityViewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        profileUserCase = ProfileUseCase(mainActivityViewmodel, this)
        profileUserCase.setUserData()

        modalViewmodel = ViewModelProvider(this).get(ModalViewModel::class.java)
        modalViewmodel.getModalState().observe(this, Observer<ModalViewModel.ModalState> { newState ->
            when(newState) {
                ModalViewModel.ModalState.REQUEST_MEETING -> {
                    modalConstraint.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.modalFrameLayout, RequestMeetingFragment())
                        commit()
                    }
                }
                ModalViewModel.ModalState.CLOSED -> {
                    modalConstraint.visibility = View.GONE
                }
            }
        })




    }

    fun openCloseNavigationDrawer(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}