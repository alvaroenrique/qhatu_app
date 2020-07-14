package com.example.qhatu.model

import com.google.firebase.firestore.FirebaseFirestore

class ListadoComprasManager {
    fun obtenerProductos(databaseReference : FirebaseFirestore, block:(lista:ArrayList<Categorias>) -> Unit) {

        var id : Long = 1
        val lista = ArrayList<Categorias>()
        val collection = databaseReference.collection("product-categories")
        collection.get().addOnSuccessListener {
            for (doc in it){
                lista.add(Categorias(id, doc.id, doc["name"] as String))
                id++
            }
            block(lista)
        }

    }
}