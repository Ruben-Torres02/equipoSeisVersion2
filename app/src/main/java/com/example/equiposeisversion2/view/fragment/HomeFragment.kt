package com.example.equiposeisversion2.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equiposeisversion2.R
import com.example.equiposeisversion2.databinding.FragmentHomeBinding
import com.example.equiposeisversion2.view.adapter.InventoryAdapter
import com.example.equiposeisversion2.viewmodel.InventoryViewModel
import com.example.equiposeisversion2.viewmodel.LoginViewModel
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class  HomeFragment : Fragment() {

    @Inject
    lateinit var apiServiceRaza: ApiServiceRaza

    private lateinit var binding: FragmentHomeBinding
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observeListInventory()
        handleBackPress()
        observeLogout()
    }

    private fun controladores() {
        binding.fabAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun observeListInventory() {
        inventoryViewModel.getListInvMascotas()
        inventoryViewModel.listaMascotas.observe(viewLifecycleOwner){ listaMascotas ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = InventoryAdapter(listaMascotas, findNavController(), apiServiceRaza)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
    private fun observeLogout() {
        loginViewModel.logoutState.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) {
                requireActivity().finishAffinity()
            }
        }
    }
    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    .edit().clear().apply()
                loginViewModel.signOut()
                requireActivity().finishAffinity()
            }
        })
    }

}
