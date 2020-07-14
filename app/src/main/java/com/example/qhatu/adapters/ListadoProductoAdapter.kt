package com.example.qhatu.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.qhatu.R
import com.example.qhatu.model.Producto

class ListadoProductoAdapter : BaseAdapter {
    var productos : ArrayList<Producto>? = null
    var inflater : LayoutInflater? = null

    constructor(context : Context, lista: List<Producto>){
        productos = lista as ArrayList<Producto>
        inflater = LayoutInflater.from(context)
        Log.i("quatu", "ENTRO AQUI")
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = null
        var viewHolder : ViewHolder? = null

        if(convertView == null){
            view= inflater!!.inflate(R.layout.item_product, null)

            viewHolder = ViewHolder()

            viewHolder.teviCategoryName = view.findViewById(R.id.teviCategoryProductName)

            view.tag = viewHolder //sirve para almacenar el viewHolder con las referencias del textView que habiamos inflado
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val producto = productos!!.get(position)
        viewHolder.teviCategoryName!!.text = producto.nombre

        return view!!
    }

    override fun getItem(position: Int): String {
        val data = productos!!.get(position).nombre + "," + productos!!.get(position).detalle
        return data
    }

    override fun getItemId(position: Int): Long {
        return productos!!.get(position).id_item
    }

    override fun getCount(): Int {
        return productos!!.size
    }

    class ViewHolder {
        var teviCategoryName : TextView? = null
    }
}