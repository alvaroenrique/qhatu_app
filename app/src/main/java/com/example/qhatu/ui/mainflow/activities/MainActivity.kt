package com.example.qhatu.ui.mainflow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.qhatu.model.Categorias
import com.example.qhatu.model.ListadoComprasManager
import com.example.qhatu.model.ListadoProductoManager
import com.example.qhatu.model.Producto
import com.example.qhatu.viewmodel.AuthenticationViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mlistarCategorias : ListView? = null
    private var mlistarProductos : ListView? = null
    private var db : FirebaseFirestore? = null

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var listener: NavController.OnDestinationChangedListener

    private lateinit var mainActivityViewmodel: MainActivityViewModel

    private lateinit var profileUserCase: ProfileUseCase

    private lateinit var authViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        profileUserCase.setUserData()



        Log.i("usurrrrrrr", authViewModel.getUserLiveData().value?.uid.toString())


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
        var listaProducto = ArrayList<Producto>()
        db = FirebaseFirestore.getInstance()
        ListadoProductoManager().obtenerListaProducto(id_clickeado, db as FirebaseFirestore){
            listaProducto = it
            mlistarProductos = findViewById(R.id.liviPLProduct)
            mlistarProductos?.adapter = ListadoProductoAdapter(this, listaProducto)
        }
    }


    fun openCloseNavigationDrawer(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}