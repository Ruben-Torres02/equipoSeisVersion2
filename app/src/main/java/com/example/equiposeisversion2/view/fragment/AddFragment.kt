package com.example.equiposeisversion2.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.FragmentAddBinding
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.viewmodel.InventoryViewModel
import kotlinx.coroutines.launch

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarControladores()
        cargarRazas()
    }

    private fun configurarControladores() {
        val sintomasArray = resources.getStringArray(R.array.sintomasMascota)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sintomasArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sintomasMascota.adapter = adapter

        binding.tvAnterior.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        binding.btnGuardar.setOnClickListener {
            lifecycleScope.launch {
                val nuevoTurno = viewModel.obtenerSiguienteTurno()

                val mascota = InventoryMascota(
                    nameMascota = binding.tvNombreRaza.text.toString(),
                    raza = binding.tvRazaEdit.text.toString(),
                    namePropietario = binding.etNombrePropietarioText.text.toString(),
                    telefono = binding.etTelfonoEdit.text.toString(),
                    sintomas = binding.sintomasMascota.selectedItem?.toString() ?: "",
                    quantity = nuevoTurno
                )

                viewModel.guardarMascota(mascota)
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }
    }

    private fun cargarRazas() {
        viewModel.razas.observe(viewLifecycleOwner) { razasMap ->
            val razasList = razasMap.keys.toList()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, razasList)
            binding.tvRazaEdit.setAdapter(adapter)
        }

        viewModel.obtenerRazas()
    }
}
