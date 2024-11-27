package com.example.examen2_stm.repositories

import com.example.examen2_stm.entities.CommentEntity
import com.example.examen2_stm.entities.PostEntity
import com.example.examen2_stm.entities.UsuarioEntity
import com.example.examen2_stm.network.ClienteRetrofit
import com.example.examen2_stm.services.UsuarioService


class UsuarioRepository(private val usuarioService: UsuarioService = ClienteRetrofit.getInstanciaRetrofit) {

    suspend fun getUsuarios() : List<UsuarioEntity> = usuarioService.getUsuarios()

    suspend fun getPosts(id: Long) : List<PostEntity> = usuarioService.getPosts(id)

    suspend fun getComments(id: Long) : List<CommentEntity> = usuarioService.getComments(id)


}