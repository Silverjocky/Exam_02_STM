package com.example.examen2_stm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.entities.UsuarioEntity

class CustomAdapterRecycler(private var usuarioList: List<UsuarioEntity>
) : RecyclerView.Adapter<CustomAdapterRecycler.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(usuario: UsuarioEntity)
        fun onItemLongClick(usuario: UsuarioEntity)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvUserName: TextView = view.findViewById(R.id.tvUsername)
        val tvEmail: TextView = view.findViewById(R.id.tvTittle)
        val tvPhone: TextView = view.findViewById(R.id.tvBody)

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(usuarioList[adapterPosition])
            }
            itemView.setOnLongClickListener {
                itemClickListener.onItemLongClick(usuarioList[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_list_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = usuarioList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarioList[position]
        holder.tvName.text = usuario.name
        holder.tvUserName.text = "username: ${usuario.username}"
        holder.tvEmail.text = "email: ${usuario.email}"
        holder.tvPhone.text = "telefono: ${usuario.phone}"

    }

    fun updateUsuariosList(newUsuariosList: List<UsuarioEntity>) {
        usuarioList = newUsuariosList
        notifyDataSetChanged()
    }

}