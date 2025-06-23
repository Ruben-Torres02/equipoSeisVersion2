package com.example.equiposeisversion2.viewmodel



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.equiposeisversion2.model.UserRequest
import com.example.equiposeisversion2.model.UserResponse
import com.example.equiposeisversion2.repository.LoginRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.verify
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.any


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val loginRepository = mock<LoginRepository>()
    private lateinit var loginViewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(loginRepository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test registrar`() = runTest {
        val userRequest = UserRequest(email = "torres@gmail.com", password = "123456")
        val expectedResponse = UserResponse(email = "torres@gmail.com", isRegister = true, message = "Registro exitoso")

        doAnswer { invocation ->
            val callback = invocation.getArgument<(UserResponse) -> Unit>(1)
            callback(expectedResponse)
            null
        }.`when`(loginRepository).registerUser(eq(userRequest), any())

        loginViewModel.registerUser(userRequest)

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedResponse, loginViewModel.isRegister.value)
    }

    @Test
    fun `test de login`() {
        val email = "torres@gmail.com"
        val password = "123456"
        val expectedResult = true

        // Llamamos a loginUser y capturamos el callback
        loginViewModel.loginUser(email, password) { result ->
            // Verificamos que el resultado sea el esperado
            assertEquals(expectedResult, result)
        }

        // Verificamos que el metodo en el repositorio fue llamado correctamente
        verify(loginRepository).loginUser(eq(email), eq(password), any())
    }

}