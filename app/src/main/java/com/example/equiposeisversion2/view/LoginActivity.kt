package com.example.equiposeisversion2.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.ActivityLoginBinding
import com.example.equiposeisversion2.model.UserRequest
import com.example.equiposeisversion2.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        sesion()
        setup()
        viewModelObservers()
        setupTextValidation()
    }

    private fun viewModelObservers() {
        observerIsRegister()
    }
    private fun observerIsRegister() {
        loginViewModel.isRegister.observe(this) { userResponse ->
            if (userResponse.isRegister) {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putString("email",userResponse.email).apply()
                goToHome()
            } else {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setup() {
        binding.btnRegister.setOnClickListener{
            registerUser()
        }

        binding.btnLogin.setOnClickListener{
            loginUser()
        }
    }

    private fun registerUser() {
        val email = binding.etCorreo.text.toString()
        val pass = binding.etPassword.text.toString()
        val userRequest = UserRequest(email, pass)

        if(email.isNotEmpty() && pass.isNotEmpty()) {
            loginViewModel.registerUser(userRequest)
        }else {
            Toast.makeText(this, "Campos Vacios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHome(){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun loginUser(){
        val email = binding.etCorreo.text.toString()
        val pass = binding.etPassword.text.toString()
        loginViewModel.loginUser(email,pass){ isLogin ->
            if (isLogin){
                sharedPreferences.edit().putString("email",email).apply()
                goToHome()
            }else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sesion() {
        val currentUser = loginViewModel.getCurrentUser()

        if (currentUser != null) {
            binding.clContenedor.visibility = View.INVISIBLE
            goToHome()
        }
    }

    private fun setupTextValidation() {
        val emailField = binding.etCorreo
        val passwordField = binding.etPassword
        val loginButton = binding.btnLogin

        val textWatcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = emailField.text.toString().trim()
                val password = passwordField.text.toString().trim()
                loginButton.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        }

        emailField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)
    }

}