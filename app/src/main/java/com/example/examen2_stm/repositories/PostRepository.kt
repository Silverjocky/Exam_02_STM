package com.example.examen2_stm.repositories

import com.example.examen2_stm.entities.CommentEntity
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.entities.UsuarioEntity
import com.example.examen2_stm.network.ClienteRetrofit
import com.example.examen2_stm.services.UsuarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostRepository(private val postService: UsuarioService = ClienteRetrofit.getInstanciaRetrofitPost) {
    suspend fun getPosts(id : Long) : List<PostEntity> = postService.getPosts(id)

}
