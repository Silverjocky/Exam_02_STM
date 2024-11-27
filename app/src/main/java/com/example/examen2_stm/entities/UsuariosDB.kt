package com.example.examen2_stm.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("usuarios")

data class UsuariosDB(
    @PrimaryKey(autoGenerate = true) val id: Long,
   @ColumnInfo("nombre") val name: String,
    @ColumnInfo("nombre de usuario") val username: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("telefono")  val phone: String
)
