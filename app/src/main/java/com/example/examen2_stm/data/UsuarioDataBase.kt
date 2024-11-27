package com.example.examen2_stm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examen2_stm.entities.UsuarioEntity
import com.example.examen2_stm.entities.UsuariosDB


@Database(entities = [UsuariosDB::class], version = 2)
abstract class UsuarioDataBase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO


    companion object {
        @Volatile
        private var INSTANCE: UsuarioDataBase? = null

        fun getDatabase(context: Context): UsuarioDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDataBase::class.java,
                    "usuario_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}