package com.example.messanger.presentation.Adapters.MessageAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messanger.R
import de.hdodenhof.circleimageview.CircleImageView

class ViewHolderMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textMessage = itemView.findViewById<TextView>(R.id.message)
    val imageUser = itemView.findViewById<CircleImageView>(R.id.myImageUser)

}