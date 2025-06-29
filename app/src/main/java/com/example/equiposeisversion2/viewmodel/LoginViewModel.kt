package com.example.equiposeisversion2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equiposeisversion2.model.UserRequest
import com.example.equiposeisversion2.model.UserResponse
import com.example.equiposeisversion2.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _isRegister = MutableLiveData<UserResponse>()
    val isRegister: LiveData<UserResponse> = _isRegister
    private val _logoutState = MutableLiveData<Boolean>()
    val logoutState: LiveData<Boolean> = _logoutState

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            loginRepository.registerUser(userRequest) { userResponse ->
                _isRegister.value = userResponse
            }
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
            loginRepository.loginUser(email, pass, isLogin)
    }



    fun signOut() {
        loginRepository.signOut()
        _logoutState.value = true
    }

    fun getCurrentUser(): FirebaseUser? {
        return loginRepository.getCurrentUser()
    }


}
