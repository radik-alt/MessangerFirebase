package com.example.messanger.presentation.Adapters.MessageAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.messanger.R
import com.example.messanger.data.Entity.Message
import com.example.messanger.data.Entity.User
import com.google.firebase.auth.FirebaseAuth

class AdapterMessage(
    private val list: ArrayList<Message>,
    private val user: User
) : RecyclerView.Adapter<ViewHolderMessage>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMessage {
        val layout = when (viewType){
            MESSAGE_ME -> R.layout.item_my_message
            else -> R.layout.item_message
        }
        context = parent.context
        val view = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolderMessage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderMessage, position: Int) {

        holder.textMessage.text = list[position].text
        Glide.with(context)
            .load(user.uriImage)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imageUser)
    }

    override fun getItemCount(): Int = list.size

    private val auth = FirebaseAuth.getInstance().uid.toString()

    override fun getItemViewType(position: Int): Int {
        return if (list[position].fromId == auth){
            MESSAGE_ME
        } else {
            MESSAGE_OTHER
        }
    }


    companion object{
        private const val MESSAGE_ME = 0
        private const val MESSAGE_OTHER = 1
    }
}