package com.example.equiposeisversion2.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.repository.InventoryRepository
import kotlinx.coroutines.launch
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: InventoryRepository
) : ViewModel() {



    private val _listaMascotas = MutableLiveData<MutableList<InventoryMascota>>()
    val listaMascotas: LiveData<MutableList<InventoryMascota>> get() = _listaMascotas

    private val _razas = MutableLiveData<Map<String, List<String>>>()
    val razas: LiveData<Map<String, List<String>>> = _razas

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState


    fun cargarMascotas() {
        viewModelScope.launch {
            val lista = repository.getListInv()

            Log.d("ViewModel", "Mascotas cargadas: ${lista.size}")
            _listaMascotas.postValue(lista)
        }
    }

    fun guardarMascota(mascota: InventoryMascota) {
        viewModelScope.launch {
            repository.saveInvMascota(mascota)
            cargarMascotas()
        }
    }

    fun obtenerRazas() {
        viewModelScope.launch {
            _razas.postValue(repository.getRaza())
        }
    }

    fun getListInvMascotas() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listaMascotas.value = repository.getListInv()
                _progresState.value = false
            }catch (e: Exception){
                _progresState.value = false
            }
        }
    }

    fun updateInventory(inventoryMascota: InventoryMascota) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                repository.updateInventory(inventoryMascota)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun deleteInventory(inventoryMascota: InventoryMascota) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                repository.deleteInventory(inventoryMascota)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

}
