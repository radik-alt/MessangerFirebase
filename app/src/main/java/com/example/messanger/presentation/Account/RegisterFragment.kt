package com.example.messanger.presentation.Account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.messanger.R
import com.example.messanger.data.Entity.User
import com.example.messanger.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding?=null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    private val viewModelRegister: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance().getReference()
    private val database = FirebaseDatabase.getInstance()

    private var uriImage: Uri?=null
    val uid = auth.uid.toString()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileImage.setOnClickListener {
            val intentImage = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intentImage, IMAGE_INTENT)
        }

        binding.clickRegister.setOnClickListener {
            createAccount()
        }

        binding.googleRegister.setOnClickListener {
        }

        binding.facebookRegister.setOnClickListener {
        }

    }

    private fun createAccount2(email:String, password:String, nameOf:String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                putUser(nameOf)
            } else if (it.isCanceled){
            }
        }
    }



    private fun putUser(name: String){
        val refDatabase = database.getReference("Users/$uid")
        refDatabase.setValue(User(uid, name, uriImage.toString())).addOnCompleteListener {
            if (it.isSuccessful){
                putImage()
            }
            Log.d("ResultFirebase", it.isSuccessful.toString())
        }
    }

    private fun putImage(){
        val refStorage = storage.child("images/$uid")
        uriImage?.let { refStorage.putFile(it).addOnSuccessListener {

        } }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        uriImage = data?.data
        Glide.with(this)
            .load(data?.data)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.profileImage)
    }

    private fun createAccount(){
        val name = binding.firstName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        if (valid(email, password, uriImage)){
            createAccount2(email, password, name)
        } else {
            Toast.makeText(requireContext(), "Введите корректные данные...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun valid(email:String, password:String, uriImage: Uri?):Boolean{
        return !(email.isEmpty() && password.isEmpty() && uriImage != null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val IMAGE_INTENT = 0
    }
}