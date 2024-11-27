package com.example.examen2_stm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.entities.CommentEntity
import com.example.examen2_stm.repositories.CommentRepository
import com.example.examen2_stm.services.CustomAdapterCommentRecycler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentActivity : AppCompatActivity() {

    private val commentRepository = CommentRepository()
    private lateinit var recyclerView: RecyclerView
    private var adapter: CustomAdapterCommentRecycler = CustomAdapterCommentRecycler(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        recyclerView = findViewById(R.id.rvComment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Obtén el postId del Intent
        val postId = intent.getStringExtra("postId")?.toLongOrNull()
        if (postId != null) {
            obtenerComentarios(postId)
        } else {
            Toast.makeText(this, "No se recibió un ID de post", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerComentarios(postId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val comments = commentRepository.getComments(postId)
                withContext(Dispatchers.Main) {
                    actualizarUI(comments)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CommentActivity, "Error al obtener comentarios", Toast.LENGTH_SHORT).show()
                    Log.e("CommentActivity", "Error: ${e.message}")
                }
            }
        }
    }

    private fun actualizarUI(comments: List<CommentEntity>) {
        adapter.updateCommentsList(comments)
    }
}
