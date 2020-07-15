package com.example.qhatu.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import java.util.*

data class Categorias(val id_item: Long, val id_doc: String, val nombre: String)

data class Producto(val id_item: Long, val id_doc: String, val nombre: String, val detalle: String)

data class DeliveryDate(
    val docId: String,
    val docReference: DocumentReference,
    val deliverDateInfo: DeliveryDateInfo
)

data class DeliveryDateInfo(
    val available: Boolean = false,
    val date: Timestamp = Timestamp(Date()),
    val limit: Long = 0
)

data class HistoricalOrder(
    val item_id : Long = 0,
    val doc_id : String = "",
    val historicalOrderInfo : HistoricalOrdeInfo? = null
)

data class HistoricalOrdeInfo(
    val date : Timestamp = Timestamp(Date()),
    val purchaseList : Map<String, Any>? = null,
    val id : Long? = null
)