package com.example.messanger.presentation.Adapters.ListMessage

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.messanger.R
import com.example.messanger.data.Entity.User
import com.example.messanger.databinding.ItemUserBinding
import com.example.messanger.presentation.Adapters.Interface.ShowMessageWithUser

class AdapterListMessage(
    private val listMessage: ArrayList<User>,
    private val showMessageWithUser: ShowMessageWithUser
) : RecyclerView.Adapter<ViewHolderListMessage>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListMessage {
        context = parent.context
        val view = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolderListMessage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderListMessage, position: Int) {

        if (listMessage[position].uriImage.isNotEmpty()){
            Glide.with(context)
                .load(listMessage[position].uriImage)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.image)
        }

        holder.itemView.setOnClickListener {
            showMessageWithUser.getUserListMessage(listMessage[position])
        }

        holder.userName.text = listMessage[position].name
    }

    override fun getItemCount(): Int = listMessage.size


}