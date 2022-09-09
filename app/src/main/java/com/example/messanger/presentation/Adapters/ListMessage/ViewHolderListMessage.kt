package com.example.messanger.presentation.Adapters.ListMessage

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.messanger.databinding.ItemUserBinding

class ViewHolderListMessage(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    val image = binding.imageUser
    val userName = binding.nameUser

}