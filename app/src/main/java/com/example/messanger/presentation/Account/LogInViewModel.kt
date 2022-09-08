package com.example.messanger.presentation.Account

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInViewModel(

) : ViewModel() {

    companion object{
        const val TAG_AUTH = "TAG_AUTH"
    }

    private val auth = FirebaseAuth.getInstance()

    fun authUser(email:String, password:String):Auth{
        var request:Auth = Auth.Error
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            request = if (it.isSuccessful){
                Auth.Success
            } else{
                Auth.Error
            }

            Log.d(TAG_AUTH, it.result.toString())
        }
        return request
    }

}

sealed class Auth{

    object Error: Auth()
    object Success: Auth()
}