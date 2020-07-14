package com.example.qhatu.model

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ListadoProductoManager {
    fun obtenerListaProducto(uid : String,id_categoria_clickeada : String, databaseReference : FirebaseFirestore, block:(lista:ArrayList<Producto>) -> Unit) {

        var id : Long = 1
        val lista = ArrayList<Producto>()
        val product_ref = databaseReference.collection("users")
        product_ref.document(uid).get().addOnSuccessListener {
                    val collection = product_ref.document(uid).collection("purchase-list")
                    collection.get().addOnSuccessListener {
                        for (doc_2 in it) {
                            val id_categoria = (doc_2["product-category"] as DocumentReference).id
                            if (id_categoria == id_categoria_clickeada) {
                                val prod =
                                    doc_2["quantity"].toString() + " " + doc_2["product-name"].toString() +
                                            " " + "de " + doc_2["measurement-quantity"].toString() + " " + doc_2["measurement-unit"].toString()
                                val detalle = doc_2["detail"].toString()
                                lista.add(Producto(id, doc_2.id, prod, detalle))
                                id++
                            }
                        }
                        block(lista)
                    }

            Log.i("quatu", lista.toString())
        }

    }
}