package com.example.equiposeisversion2.viewmodel

import org.mockito.kotlin.any
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.repository.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class InventoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<InventoryRepository>()
    private lateinit var viewModel: InventoryViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = InventoryViewModel(repository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test deleteInventory`() = runTest {
        val mascota = InventoryMascota(
            id = "abec25145",
            nameMascota = "Milan",
            raza = "pug",
            namePropietario = "Ruben Torres",
            telefono = "3137926659",
            sintomas = "duerme mucho",
            numberId = 2
        )

        viewModel.deleteInventory(mascota)


        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).deleteInventory(mascota)
    }


    @Test
    fun `test guardarMascota `() = runTest {
        // Arrange
        val mascota = InventoryMascota(
            id = "1asdac",
            nameMascota = "Milan",
            raza = "Pug",
            namePropietario = "Rubén Torres",
            telefono = "3137926659",
            sintomas = "Duerme mucho",
            numberId = 1
        )

        // Act
        viewModel.guardarMascota(mascota)

        // Avanza la ejecución de la corrutina lanzada en viewModelScope
        advanceUntilIdle()

        // Assert
        verify(repository).saveInvMascota(mascota)
    }

    @Test
    fun `test lista de mascotas `() = runTest {
        val mockList = listOf(
            InventoryMascota(
                id = "3xl",
                nameMascota = "princesa",
                namePropietario = "Alejandra",
                telefono = "3126696969",
                sintomas = "tiene pulgas",
                numberId = 5
            )
        )
        doAnswer { invocation ->
            val callback = invocation.arguments[0] as (List<InventoryMascota>) -> Unit
            callback(mockList)
            null
        }.whenever(repository).getListInv(any())

        // When
        viewModel.getListInvMascotas()

        // Then
        assert(viewModel.listaMascotas.value?.isNotEmpty() == true)
        assert(viewModel.progresState.value == false)
    }

}

