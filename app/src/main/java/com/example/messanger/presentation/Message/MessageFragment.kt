package com.example.messanger.presentation.Message

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.messanger.R
import com.example.messanger.data.Entity.Message
import com.example.messanger.data.Entity.User
import com.example.messanger.data.RepositoryFirebase
import com.example.messanger.databinding.FragmentMessageBinding
import com.example.messanger.databinding.FragmentNewMessageBinding
import com.example.messanger.presentation.Adapters.MessageAdapter.AdapterMessage
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase


class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding?=null
    private val binding: FragmentMessageBinding
        get() = _binding ?: throw RuntimeException("FragmentMessageBinding == null")

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val database = FirebaseDatabase.getInstance()
    private var user: User?=null
    private val listMessage = ArrayList<Message>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedViewModel.getUser().observe(viewLifecycleOwner){
            user = it
        }

        database.reference.child("message/").addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val res = snapshot.getValue(Message::class.java)
                if (res != null) {
                    listMessage.add(res)
                }
                setAdapter()
                Log.d("GetMessageFirebase", listMessage.toString())
                Log.d("GetMessageFirebase", res.toString())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        binding.sendMessage.setOnClickListener {
            val textMessage = binding.messageText.text.toString()
            if (textMessage.isNotEmpty()){
                val message = user?.uid?.let {
                    Message(
                        fromId = RepositoryFirebase.uid,
                        toId = it,
                        text = textMessage,
                        dateSend = System.currentTimeMillis().toInt()
                    )
                }

                database.getReference("message").push().setValue(message).addOnCompleteListener {
                    Log.d("GetMessageFirebase", it.toString())
                }
            }
        }
    }

    private fun setAdapter(){
        val adapter = user?.let { AdapterMessage(listMessage, it) }
        binding.recyclerMessanger.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}