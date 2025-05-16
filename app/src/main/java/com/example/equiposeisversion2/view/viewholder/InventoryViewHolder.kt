package com.example.equiposeisversion2.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equiposeisversion2.databinding.ItemInventoryBinding
import com.example.equiposeisversion2.model.InventoryMascota

class InventoryViewHolder(
    private val binding: ItemInventoryBinding,
    private val navController: NavController
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemInventory(inventoryMascota: InventoryMascota, imageUrl: String) {
        binding.tvNombreMascota1.text = inventoryMascota.nameMascota
        binding.tvSintoma.text = inventoryMascota.sintomas
        binding.tvTurno.text ="# ${inventoryMascota.quantity}"

        Glide.with(binding.imgMascota.context)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(binding.imgMascota)

    }
}