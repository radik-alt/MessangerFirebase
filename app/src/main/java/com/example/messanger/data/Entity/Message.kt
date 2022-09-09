package com.example.messanger.data.Entity

data class Message(
    val fromId: String = "",
    val toId: String = "",
    val text: String = "",
    val dateSend: Int = -1,
)
