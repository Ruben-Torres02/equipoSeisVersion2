package com.example.equiposeisversion2.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        validacionCampos()
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
            val sintomaSeleccionado = binding.sintomasMascota.selectedItem?.toString()?.trim() ?: ""

            if (binding.sintomasMascota.selectedItemPosition == 0 || sintomaSeleccionado.equals("Selecciona un síntoma", ignoreCase = true)) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Atención")
                    .setMessage("Selecciona un síntoma.")
                    .setPositiveButton("Aceptar", null)
                    .show()
                return@setOnClickListener
            }
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
                binding.btnGuardar.isEnabled = todosLlenos

                if(todosLlenos) {
                    binding.btnGuardar.isEnabled = true
                    binding.btnGuardar.setTextColor(resources.getColor(R.color.white, null))
                    binding.btnGuardar.setTypeface(null, android.graphics.Typeface.BOLD)
                }else{
                    binding.btnGuardar.isEnabled = false
                    binding.btnGuardar.setTextColor(resources.getColor(R.color.black, null))
                    binding.btnGuardar.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        camposObli.forEach{ campo -> campo.addTextChangedListener(textWatcher)
        }
        binding.btnGuardar.isEnabled = false
        binding.btnGuardar.setTextColor(resources.getColor(R.color.black, null))
        binding.btnGuardar.setTypeface(null, android.graphics.Typeface.NORMAL)
    }
}
