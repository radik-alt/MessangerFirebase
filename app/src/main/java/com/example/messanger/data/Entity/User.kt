package com.example.messanger.data.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val name: String = "",
    val uriImage:String = ""
) : Parcelable
