package com.example.equiposeisversion2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.FragmentLoginBinding
import java.util.concurrent.Executor


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
    }

    private fun controladores() {
        binding.lottieView.setOnClickListener {
            showBiometricPrompt()
        }
    }

    private fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(requireContext())

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }


                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("Biometria", "Error de autenticación: $errString")

                    Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "el error es propio del dispositivo", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación con Biometría")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }


}