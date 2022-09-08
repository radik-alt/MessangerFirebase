package com.example.messanger.presentation.Account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.messanger.R
import com.example.messanger.data.Entity.User
import com.example.messanger.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding?=null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    private val viewModelLogIn: LogInViewModel by lazy {
        ViewModelProvider(this)[LogInViewModel::class.java]
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerIntent.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }

        binding.LogIN.setOnClickListener {
            val email = binding.emailAuth.text.toString()
            val password = binding.password.text.toString()
            if (valid(email, password)){
                authUser(email ,password)
//                val request = viewModelLogIn.authUser(email, password)
//                when (request){
//                    is Auth.Error -> {
//                        Toast.makeText(requireContext(), "Ошибка авторизации...", Toast.LENGTH_SHORT).show()
//                    }
//                    is Auth.Success -> {
//                        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
//                    }
//                }
            }
        }
    }


    private fun authUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
            }
        }
    }

    private fun valid(email:String, password: String):Boolean{
        if (email.isNotBlank() && password.isNotBlank()) return true

        Toast.makeText(requireContext(), "Введите корректные данные!", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}