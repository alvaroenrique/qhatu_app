package com.example.qhatu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.qhatu.R
import com.example.qhatu.model.HistoricalOrder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListadoOrdenesHistoricasAdapter : BaseAdapter {

    private var historicOrders : ArrayList<HistoricalOrder>? = null
    private var inflater : LayoutInflater? = null

    constructor(context:Context, lista: ArrayList<HistoricalOrder>){
        historicOrders = lista
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = null

        var viewHolder : ViewHolder? = null

        if (convertView == null){
            view = inflater!!.inflate(R.layout.item_historic_purchases, null)

            viewHolder = ViewHolder()

            viewHolder.tviFecha =view!!.findViewById<TextView>(R.id.teviHistoricalPurchaseName)

            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val fecha = historicOrders!!.get(position)

        val date = fecha.historicalOrderInfo!!.date.toDate()
        val locale = Locale("es", "PE")
        val format = SimpleDateFormat("EEEE d 'de' MMMM", locale)

        viewHolder.tviFecha!!.text = format.format(date).capitalize()

        return view!!
    }

    override fun getItem(position: Int): Any {
        return historicOrders!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return  historicOrders!!.get(position).item_id
    }

    override fun getCount(): Int {
        return historicOrders!!.size
    }

    class ViewHolder {
        var tviFecha : TextView? = null
    }
}