package com.example.messanger.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

object RepositoryFirebase {

    private val auth = FirebaseAuth.getInstance()
    val uid = auth.uid.toString()


}