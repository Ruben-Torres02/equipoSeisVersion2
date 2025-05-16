package com.example.equiposeisversion2.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equiposeisversion2.databinding.ItemInventoryBinding
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.view.viewholder.InventoryViewHolder
import com.example.equiposeisversion2.webservice.RetrofitCliente
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventoryAdapter
    (private val listaMascotas:MutableList<InventoryMascota>,
     private val navController: NavController
    ): RecyclerView.Adapter<InventoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listaMascotas.size
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val mascota = listaMascotas[position]
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
            try {
                val response = withContext(kotlinx.coroutines.Dispatchers.IO) {
                    RetrofitCliente.apiServiceRaza.getRandomImage(mascota.raza.lowercase())
                }
                holder.setItemInventory(mascota, response.message)
            } catch (e: Exception) {
                holder.setItemInventory(mascota, "") // imagen por defecto
            }
        }
    }
}