package com.example.qhatu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.qhatu.R
import com.example.qhatu.model.Categorias

class ListadoComprasAdapter : BaseAdapter {
    var categorias : ArrayList<Categorias>? = null
    var inflater : LayoutInflater? = null

    constructor(context : Context, lista: List<Categorias>){
        categorias = lista as ArrayList<Categorias>
        inflater = LayoutInflater.from(context)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = null
        var viewHolder : ViewHolder? = null

        if(convertView == null){
            view= inflater!!.inflate(R.layout.item_category, null)

            viewHolder = ViewHolder()

            viewHolder.tviCategoryName = view.findViewById<TextView>(R.id.teviCategoryName)

            view.tag = viewHolder //sirve para almacenar el viewHolder con las referencias del textView que habiamos inflado
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val categoria = categorias!!.get(position)
        viewHolder.tviCategoryName!!.text = categoria.nombre

        return view!!
    }

    override fun getItem(position: Int): String {
        val data = categorias!!.get(position).id_doc + "," + categorias!!.get(position).nombre

        return data
    }

    override fun getItemId(position: Int): Long {
        return categorias!!.get(position).id_item
    }

    override fun getCount(): Int {
        //Devolver la cantidad de elementos del modelo que se van a pintar en el listado
        return categorias!!.size
    }

    class ViewHolder {
        var tviCategoryName : TextView? = null
    }
}