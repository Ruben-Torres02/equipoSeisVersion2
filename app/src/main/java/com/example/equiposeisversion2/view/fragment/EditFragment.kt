package com.example.equiposeisversion2.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.FragmentEditBinding
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.viewmodel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private lateinit var receivedInventoryMascota: InventoryMascota

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInventory()
        controladores()
        cargarRazas()
        validacionCampos()
    }

    private fun controladores () {
        binding.btnEditar.setOnClickListener {
            updateInventory()
        }
        binding.tvAnterior.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_detailsFragment)
        }

    }

    @Suppress("DEPRECATION")
    private fun dataInventory() {
        val bundle = arguments
        receivedInventoryMascota = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            bundle?.getSerializable("dataInventory", InventoryMascota::class.java)
        } else {
            @Suppress("UNCHECKED_CAST")
            bundle?.getSerializable("dataInventory") as? InventoryMascota
        } ?: run {
            Toast.makeText(requireContext(), "No se pudo cargar la mascota", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        binding.tvNombreRaza.setText(receivedInventoryMascota.nameMascota)
        binding.etNombrePropietarioText.setText(receivedInventoryMascota.namePropietario)
        binding.etTelfonoEdit.setText(receivedInventoryMascota.telefono)
        binding.tvRazaEdit.setText(receivedInventoryMascota.raza)
    }



    private fun updateInventory() {
        val nameMascota = binding.tvNombreRaza.text.toString()
        val namePropietario = binding.etNombrePropietarioText.text.toString()
        val telefono = binding.etTelfonoEdit.text.toString()
        val raza = binding.tvRazaEdit.text.toString()

        val inventoryMascota = InventoryMascota(
            id = receivedInventoryMascota.id,
            nameMascota = nameMascota,
            raza = raza,
            namePropietario = namePropietario,
            telefono = telefono,
            sintomas = receivedInventoryMascota.sintomas
        )
        inventoryViewModel.updateInventory(inventoryMascota)
        findNavController().navigate(R.id.action_editFragment_to_homeFragment)
    }

    private fun cargarRazas() {
        inventoryViewModel.razas.observe(viewLifecycleOwner) { razasMap ->
            val razasList = razasMap.keys.toList()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, razasList)
            binding.tvRazaEdit.setAdapter(adapter)
        }

        inventoryViewModel.obtenerRazas()
    }
    private fun validacionCampos () {
        val camposObli = listOf(

            binding.tvNombreRaza,
            binding.tvRazaEdit,
            binding.etNombrePropietarioText,
            binding.etTelfonoEdit
        )
        val textWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val todosLlenos = camposObli.all { it.text.toString().isNotBlank() }
                binding.btnEditar.isEnabled = todosLlenos

                if(todosLlenos) {
                    binding.btnEditar.isEnabled = true
                    binding.btnEditar.setTextColor(resources.getColor(R.color.white, null))
                    binding.btnEditar.setTypeface(null, android.graphics.Typeface.BOLD)
                }else{
                    binding.btnEditar.isEnabled = false
                    binding.btnEditar.setTextColor(resources.getColor(R.color.black, null))
                    binding.btnEditar.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        camposObli.forEach{ campo -> campo.addTextChangedListener(textWatcher)
        }
        binding.btnEditar.isEnabled = false
        binding.btnEditar.setTextColor(resources.getColor(R.color.black, null))
        binding.btnEditar.setTypeface(null, android.graphics.Typeface.NORMAL)
    }

}