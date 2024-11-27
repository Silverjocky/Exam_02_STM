package com.example.examen2_stm


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.CustomAdapterRecycler.ViewHolder
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.entities.UsuarioEntity

class CustomAdapterPostRecycler(private var postList: List<PostEntity>
) : RecyclerView.Adapter<CustomAdapterPostRecycler.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener



    interface OnItemClickListener {
        fun onItemClick(post: PostEntity)
        fun onItemLongClick(post: PostEntity)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTittle: TextView = view.findViewById(R.id.tvTittle)
        val tvBody: TextView = view.findViewById(R.id.tvBody)


        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(postList[adapterPosition])
            }
            itemView.setOnLongClickListener {
                itemClickListener.onItemLongClick(postList[adapterPosition])
                true
            }
        }}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_post_recycler, parent, false)
        return ViewHolder(view) }


        override fun getItemCount(): Int = postList.size

        override fun onBindViewHolder(holder: com.example.examen2_stm.CustomAdapterPostRecycler.ViewHolder, position: Int) {
            val post = postList[position]
            holder.tvTittle.text = post.title
            holder.tvBody.text = post.body

        }

        fun updatePostList(newPostList: List<PostEntity>) {
            postList = newPostList
            notifyDataSetChanged()
        }

    }
