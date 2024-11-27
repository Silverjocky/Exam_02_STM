package com.example.examen2_stm.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.R
import com.example.examen2_stm.entities.CommentEntity

class CustomAdapterCommentRecycler(private var comments: List<CommentEntity>) :
    RecyclerView.Adapter<CustomAdapterCommentRecycler.ViewHolder>() {

    fun updateCommentsList(newComments: List<CommentEntity>) {
        comments = newComments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_comment_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.name.text = comment.name
        holder.email.text = comment.email
        holder.body.text = comment.body
    }

    override fun getItemCount() = comments.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvCommentName)
        val email: TextView = itemView.findViewById(R.id.tvCommentEmail)
        val body: TextView = itemView.findViewById(R.id.tvCommentBody)
    }
}
