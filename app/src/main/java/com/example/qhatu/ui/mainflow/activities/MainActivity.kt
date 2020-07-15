package com.example.qhatu.ui.mainflow.activities

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
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
import com.example.qhatu.adapters.ListadoComprasAdapter
import com.example.qhatu.adapters.ListadoProductoAdapter
import com.example.qhatu.domain.ProfileUseCase
import com.example.qhatu.model.*
import com.example.qhatu.ui.authentication.activities.AuthenticationActivity
import com.example.qhatu.ui.mainflow.fragments.AddProductDialogFragment
import com.example.qhatu.ui.mainflow.interfaces.OnDeliveryDatesNeeded
import com.example.qhatu.ui.mainflow.interfaces.OnHistoricalOrdersNeeded
import com.example.qhatu.ui.mainflow.interfaces.OnPlaceOrder
import com.example.qhatu.ui.model.User
import com.example.qhatu.viewmodel.AuthenticationViewModel
import com.example.qhatu.viewmodel.HistoricalOrdersViewModel
import com.example.qhatu.viewmodel.RequestOrderViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnDeliveryDatesNeeded, OnPlaceOrder,
    OnHistoricalOrdersNeeded {
    private var mlistarCategorias : ListView? = null
    private var mlistarProductos : ListView? = null
    private var db : FirebaseFirestore? = null

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var listener: NavController.OnDestinationChangedListener

    private lateinit var mainActivityViewmodel: MainActivityViewModel

    // RequestOrderViewModel
    private var requestOrderViewModel: RequestOrderViewModel? = null
    private var authenticationViewModel: AuthenticationViewModel? = null
    private var availableDeliveryDateArrayList: ArrayList<DeliveryDate>? = null
    private var historicalOrderViewModel: HistoricalOrdersViewModel? = null
    var historicalOrdersArrayList: ArrayList<HistoricalOrder>? = null

    private lateinit var profileUserCase: ProfileUseCase

    private lateinit var authViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAuthenticationViewModel()
        setUpRequestOrderViewModel()
        setUpHistoricalOrdersViewModel()
        requestOrderViewModel!!.getAvailableDeliveryDateArray { }

        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

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
        mainActivityViewmodel.setUid(authViewModel.getUserLiveData().value?.uid)
        profileUserCase = ProfileUseCase(mainActivityViewmodel, this)
        profileUserCase.defaultPicture(BitmapFactory.decodeResource(
            resources,
            R.drawable.bg_menu_dark))
        profileUserCase.setUserData()


        initAddButton()

        val authenticationObserver = Observer<User> {
            if(it==null){
                goToAuthenticationActivity()
            }
        }

        authViewModel?.getUserLiveData()?.observe(this, authenticationObserver)

    }

    fun setUpRequestOrderViewModel() {
        requestOrderViewModel = RequestOrderViewModel()
        val availableDeliveryDatesObserver = Observer<ArrayList<DeliveryDate>> {
            // Hacer algo
        }
        requestOrderViewModel!!.getAvailableDeliveryDateArrayDataLiveData()
            .observe(this, availableDeliveryDatesObserver)
    }

    fun setAuthenticationViewModel() {
        authenticationViewModel = AuthenticationViewModel()
    }

    fun setUpHistoricalOrdersViewModel() {
        historicalOrderViewModel = HistoricalOrdersViewModel()

        val historicalOrdersObserver = Observer<ArrayList<HistoricalOrder>> {
            historicalOrdersArrayList = it
        }

        historicalOrderViewModel!!.getHistoricalOrdersLiveData()
            .observe(this, historicalOrdersObserver)
    }

    fun ListarCategorias(){
        var listaCategorias  = ArrayList<Categorias>()

        db = FirebaseFirestore.getInstance()
        ListadoComprasManager().obtenerProductos(db as FirebaseFirestore) {
            listaCategorias = it
            mlistarCategorias = findViewById(R.id.liviPL)
            mlistarCategorias?.adapter = ListadoComprasAdapter(this, listaCategorias)
            Log.i(javaClass.canonicalName, listaCategorias.toString())
        }
    }

    fun ListasProductos(id_clickeado:String){
        mainActivityViewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val uid = mainActivityViewmodel.getUid().value
        var listaProducto = ArrayList<Producto>()
        db = FirebaseFirestore.getInstance()
        ListadoProductoManager().obtenerListaProducto(uid.toString(), id_clickeado, db as FirebaseFirestore){
            listaProducto = it
            mlistarProductos = findViewById(R.id.liviPLProduct)
            mlistarProductos?.adapter = ListadoProductoAdapter(this, listaProducto)
            Log.i("quatu", "ESTO ES ->" + uid)
        }
    }


    fun openCloseNavigationDrawer(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    fun initAddButton() {
        iconAdd.setOnClickListener {
            val fragmentManager =  supportFragmentManager
            val newFragment = AddProductDialogFragment()
            newFragment.show(fragmentManager, "dialog")
        }

        mainActivityViewmodel.getAddIconVisible().observe(this, Observer<Boolean> { newState ->
            if (newState) {
                iconAdd.visibility = View.VISIBLE
            } else {
                iconAdd.visibility = View.GONE
            }
        })
    }

    fun goToAuthenticationActivity(){
        val intent = Intent()
        intent.setClass(this, AuthenticationActivity::class.java)
        startActivityForResult(intent, 1000)
        this.finish()
    }

    override fun getDeliveryDates(): ArrayList<DeliveryDate>? {
        return availableDeliveryDateArrayList
    }

    override fun placeOrder(
        deliveryDate: DeliveryDate,
        paymentMethod: String,
        superMarket: String,
        block: (success: Boolean) -> Unit
    ) {
        requestOrderViewModel!!.placeOrder(
            deliveryDate,
            paymentMethod,
            superMarket,
            block,
            authenticationViewModel!!.getUserLiveData().value!!
        )
    }

    override fun getHistoricalOrders(
        block: (success: Boolean) -> Unit,
        blockF: (orderList: ArrayList<HistoricalOrder>) -> Unit
    ) {
        historicalOrderViewModel!!.getHistoricalOrdersData(
            authenticationViewModel!!.getUserLiveData().value!!.uid!!,
            block,
            blockF
        )
    }
}