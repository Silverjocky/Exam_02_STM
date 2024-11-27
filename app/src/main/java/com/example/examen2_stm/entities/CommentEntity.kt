package com.example.examen2_stm.entities

data class CommentEntity(
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
