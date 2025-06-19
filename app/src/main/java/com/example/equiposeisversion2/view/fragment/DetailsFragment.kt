package com.example.equiposeisversion2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.FragmentDetailsBinding
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.viewmodel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val inventoryViewModel: InventoryViewModel by viewModels ()
    private lateinit var receivedInventoryMascota: InventoryMascota

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        dataInventory()
    }

    private fun controladores() {
        binding.fabEliminar.setOnClickListener{
           deleteInventory()
        }

        binding.fabEditar.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("dataInventory", receivedInventoryMascota)
            findNavController().navigate(R.id.action_detailsFragment_to_editFragment, bundle)        }

        binding.tvAnterior.setOnClickListener{
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
        }
    }

    @Suppress("DEPRECATION")
    private fun dataInventory() {
        receivedInventoryMascota = arguments?.getSerializable("clave") as? InventoryMascota
            ?: run {
                Log.e("DetailsFragment", "InventoryMascota es null")
                return
            }

        Log.d("DetailsFragment", "Recibido: $receivedInventoryMascota")

        binding.tvRazainput.text = receivedInventoryMascota.nameMascota
        binding.etNombrePropietarioText.setText(receivedInventoryMascota.namePropietario)
        binding.tvRazaEdit.text = receivedInventoryMascota.raza
        binding.etTelfonoEdit.setText(receivedInventoryMascota.telefono)
        binding.tvSintoma.text = receivedInventoryMascota.sintomas
        binding.tvTurno.text = "#${receivedInventoryMascota.numberId}"

        val imageUrl = arguments?.getString("imageUrl")

        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(binding.imgMascota)



    }

    private fun deleteInventory(){
        inventoryViewModel.deleteInventory(receivedInventoryMascota)
        inventoryViewModel.getListInvMascotas()
        findNavController().popBackStack()
    }

}