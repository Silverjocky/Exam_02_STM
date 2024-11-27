package com.example.examen2_stm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.repositories.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val postAdapter = CustomAdapterPostRecycler(emptyList())
    private val postRepository = PostRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        recyclerView = findViewById(R.id.rvComment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        // Obtén el ID del usuario desde el intent
        val userId = intent.getStringExtra("id") ?: return

        // Llama a la función para obtener los posts
        obtenerPosts(userId)
        postAdapter.setOnItemClickListener(object : CustomAdapterPostRecycler.OnItemClickListener {
            override fun onItemClick(post: PostEntity) {
                val intent = Intent(this@PostActivity, CommentActivity::class.java)
                intent.putExtra("postId", post.id.toString())
                startActivity(intent)
            }

            override fun onItemLongClick(post: PostEntity) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun obtenerPosts(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val posts = postRepository.getPosts(userId.toLong())
                withContext(Dispatchers.Main) {
                    if (posts.isNotEmpty()) {
                        postAdapter.updatePostList(posts)
                    } else {
                        Toast.makeText(this@PostActivity, "No hay posts para este usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PostActivity, "Error al cargar posts: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("PostActivity", "Error: ${e.message}")
                }
            }
        }
    }




}
