package com.example.examen2_stm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen2_stm.repositories.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_stm.data.UsuarioDataBase
import com.example.examen2_stm.entities.UsuarioEntity
import com.example.examen2_stm.entities.UsuariosDB
import kotlinx.coroutines.GlobalScope


class MainActivity : AppCompatActivity() {

    private val usuarioRepository = UsuarioRepository()
    lateinit var btnAgregar : Button

    var adapterRecycler: CustomAdapterRecycler = CustomAdapterRecycler(emptyList())
    lateinit var recyclerView: RecyclerView
    lateinit var usuariodb: UsuarioDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usuariodb = UsuarioDataBase.getDatabase(this)

        recyclerView = findViewById(R.id.rvComment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterRecycler
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener{
            lifecycleScope.launch {
                usuariodb.usuarioDao().getAll().collect{
                    it.forEach{
                        Log.d("Usuarios","Usuario ${it.name}")
                    }
                }
            }
        }


        if(isInternetAvailable(this)){
            obtenerUsuarios()
        }
        else{
            Toast.makeText(this,"No hay conexion a internet",Toast.LENGTH_SHORT).show()
            datosGuardados()
        }

        adapterRecycler.setOnItemClickListener(object : CustomAdapterRecycler.OnItemClickListener {
            override fun onItemClick(heroe: UsuarioEntity) {
                val intent = Intent(this@MainActivity, PostActivity::class.java)
                intent.putExtra("id",heroe.id.toString())
                startActivity(intent)
            }

            override fun onItemLongClick(heroe: UsuarioEntity) {
                guardarUsuario(heroe)
            }
        })

    }


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Para Android 6.0 (API 23) y superior
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            // Para versiones anteriores de Android
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun datosGuardados() {
        val newUsers = mutableListOf<UsuarioEntity>() // Inicializa la lista correctamente

        // Usa el scope adecuado, por ejemplo lifecycleScope en actividades/fragments
        lifecycleScope.launch {
            usuariodb.usuarioDao().getAll().collect { users ->
                // Limpia la lista antes de agregar nuevos datos
                newUsers.clear()
                users.forEach { user ->
                    newUsers.add(
                        UsuarioEntity(
                            id = user.id,
                            name = user.name,
                            username = user.username,
                            email = user.email,
                            phone = user.phone
                    ))
                }
                // Actualiza el adaptador después de llenar la lista
                adapterRecycler.updateUsuariosList(newUsers)
            }
        }
    }

    private fun guardarUsuario(user: UsuarioEntity) {
        GlobalScope.launch {
            try {
                usuariodb.usuarioDao().add(
                    UsuariosDB(
                        id = user.id,
                        name = user.name,
                        username = user.username,
                        email = user.email,
                        phone = user.phone
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Toast.makeText(this, "Usuario: ${user.name} \nGuardado con éxito", Toast.LENGTH_SHORT)
        .show()
    }



    private fun obtenerUsuarios() {
        // Mandar a segundo plano
        CoroutineScope(Dispatchers.IO).launch  {
            // Mandar a srgundo plano
            try{
                // Segundo plano
                val usuarios = usuarioRepository.getUsuarios()
                // Mandar a primer plano = UI - Vista
                withContext(Dispatchers.Main){
                    // Agregar el listado al list viwe o recycler view o text view
                    Toast.makeText(this@MainActivity, "Listado usuarios ${usuarios}", Toast.LENGTH_LONG).show()
                    Log.d("Usuarios", usuarios.toString())
                    adapterRecycler.updateUsuariosList(usuarios)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, "Error ${e.message.toString()}", Toast.LENGTH_LONG).show()
                    Log.d("Usuarios", "Error ${e.toString()}")
                }
            }
        }
    }
}