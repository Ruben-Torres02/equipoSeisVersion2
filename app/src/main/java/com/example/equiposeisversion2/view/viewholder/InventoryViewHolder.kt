package com.example.equiposeisversion2.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equiposeisversion2.databinding.ItemInventoryBinding
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.R

class InventoryViewHolder(
    private val binding: ItemInventoryBinding,
    private val navController: NavController
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemInventory(inventoryMascota: InventoryMascota, imageUrl: String) {
        binding.tvNombreMascota1.text = inventoryMascota.nameMascota
        binding.tvSintoma.text = inventoryMascota.sintomas
        binding.tvTurno.text ="# ${inventoryMascota.numberId}"

        binding.cardview.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("clave", inventoryMascota)
                putString("imageUrl", imageUrl)
            }
            navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }
        Glide.with(binding.imgMascota.context)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(binding.imgMascota)


    }
}