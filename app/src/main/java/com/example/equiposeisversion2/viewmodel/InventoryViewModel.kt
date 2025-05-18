package com.example.equiposeisversion2.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.model.RazaResponse
import com.example.equiposeisversion2.repository.InventoryRepository
import kotlinx.coroutines.launch
import android.util.Log

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InventoryRepository(application)

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

    suspend fun obtenerSiguienteTurno(): Int {
        return repository.contarMascotas() + 1
    }

}
