package com.example.messanger.presentation.Account

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.messanger.data.Entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterViewModel(

): ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private val database = FirebaseDatabase.getInstance()
    private val uid = auth.uid.toString()
    private var name: String ?= null

    private var uriImage: Uri?=null


    fun createAccount(email:String, password:String, uriImage: Uri?, nameOf:String): Register{

        var request: Register = Register.Success
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful){
                name = nameOf
                saveUserToFirebaseDataBase()
                Register.Success
                Log.d("RequestFirebaseAuth", "Success register!")
            } else {
                Register.Error
                Log.d("RequestFirebaseAuth", "Faile register! ${it.result.toString()}")
            }

        }


        return request
    }

    private fun uploadImageToFirebase(uriImageFull: Uri){

        val ref = storage.child("images/$uid")
        ref.putFile(uriImageFull).addOnSuccessListener {
            Log.d("RequestFirebaseStorage", it.toString())
        }.addOnFailureListener {
            Log.d("RequestFirebaseStorage", it.message.toString())
        }

    }

    private fun saveUserToFirebaseDataBase(){

        val refDatabase = database.getReference("Users/${uid}")
        if (name != null){
            val user = User(uid, name!!, uriImage.toString())
            refDatabase.setValue(user).addOnSuccessListener {
                Log.d("RequestFirebaseDataBase", it.toString())
                uriImage?.let { it1 -> uploadImageToFirebase(it1) }
            }.addOnFailureListener {
                Log.d("RequestFirebaseDataBase", it.message.toString())
            }
        }
    }

}

sealed class Register(){

    object Error: Register()
    object Success: Register()
}