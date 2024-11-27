package com.example.examen2_stm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examen2_stm.entities.UsuariosDB
import kotlinx.coroutines.flow.Flow


@Dao
interface UsuarioDAO {
    @Query("SELECT * FROM usuarios")
    fun getAll(): Flow<List<UsuariosDB>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usuarios: List<UsuariosDB>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user:UsuariosDB):Long
}
