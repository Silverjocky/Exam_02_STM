package com.example.examen2_stm.entities

import androidx.room.PrimaryKey

data class UsuarioEntity(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val phone: String
)
