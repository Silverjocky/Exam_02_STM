package com.example.examen2_stm.services

import com.example.examen2_stm.entities.CommentEntity
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.entities.UsuarioEntity
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {

    @GET("users")
    suspend fun getUsuarios(): List<UsuarioEntity>

    @GET("users/{id}/posts")
    suspend fun getPosts(@Path("id") id: Long): List<PostEntity>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): List<CommentEntity>

}