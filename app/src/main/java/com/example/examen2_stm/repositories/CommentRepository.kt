package com.example.examen2_stm.repositories

import com.example.examen2_stm.entities.CommentEntity
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.network.ClienteRetrofit
import com.example.examen2_stm.services.UsuarioService

class CommentRepository(private val commentService: UsuarioService = ClienteRetrofit.getInstanciaRetrofitComments) {
    suspend fun getComments(id : Long) : List<CommentEntity> = commentService.getComments(id)
}