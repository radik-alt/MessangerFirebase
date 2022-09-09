package com.example.messanger.presentation.Message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messanger.data.Entity.User

class SharedViewModel(

): ViewModel() {

    private val userToMessage = MutableLiveData<User>()

    fun setUser(user: User){
        userToMessage.value = user
    }

    fun getUser():LiveData<User>{
        return userToMessage
    }


}