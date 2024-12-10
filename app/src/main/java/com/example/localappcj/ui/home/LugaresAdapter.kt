package com.example.localappcj.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localappcj.R

class LugaresAdapter(private val lugares: List<Lugar>) : RecyclerView.Adapter<LugaresAdapter.LugarViewHolder>() {

    // ViewHolder: mantiene las referencias a las vistas de un elemento
    class LugarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre)
        val urlTextView: TextView = itemView.findViewById(R.id.text_url)
    }

    // Crea nuevas vistas (llamado por el LayoutManager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lugar, parent, false)
        return LugarViewHolder(view)
    }

    // Reemplaza el contenido de una vista (llamado por el LayoutManager)
    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugar = lugares[position]
        holder.nombreTextView.text = lugar.nombre
        holder.urlTextView.text = lugar.url
    }

    // Devuelve el tamaño de tu dataset (llamado por el LayoutManager)
    override fun getItemCount() = lugares.size
}
