package com.example.messanger.presentation.NewMessage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.messanger.R
import com.example.messanger.data.Entity.User
import com.example.messanger.databinding.FragmentNewMessageBinding
import com.example.messanger.databinding.FragmentRegisterBinding
import com.example.messanger.presentation.Adapters.Interface.ShowMessageWithUser
import com.example.messanger.presentation.Adapters.ListMessage.AdapterListMessage
import com.example.messanger.presentation.MainActivity
import com.example.messanger.presentation.Message.MessageFragment
import com.example.messanger.presentation.Message.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class NewMessageFragment : Fragment() {

    private var _binding: FragmentNewMessageBinding?=null
    private val binding: FragmentNewMessageBinding
        get() = _binding ?: throw RuntimeException("FragmentNewMessageBinding == null")

    private val database = FirebaseDatabase.getInstance()
    private val uid = FirebaseAuth.getInstance().uid.toString()

    private var listUser = ArrayList<User>()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refDatabase = database.getReference("Users/")
        refDatabase.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listUser.clear()

                snapshot.children.forEach {
                    loaderUser(true)
                    it.getValue(User::class.java)?.let { it1 -> listUser.add(it1) }
                }

                loaderUser(false)
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun loaderUser(loader: Boolean){
        when {
            loader -> {
                binding.adapterUserList.visibility = View.GONE
                binding.textNoUser.visibility = View.GONE
                binding.progressUser.visibility = View.VISIBLE
            }
            listUser.size == 0 -> {
                binding.adapterUserList.visibility = View.GONE
                binding.progressUser.visibility = View.GONE
                binding.textNoUser.visibility = View.VISIBLE
            }
            else -> {
                binding.adapterUserList.visibility = View.VISIBLE
                binding.progressUser.visibility = View.GONE
                binding.textNoUser.visibility = View.GONE
            }
        }
    }

    private fun setAdapter() {
        val adapter = AdapterListMessage(listUser, object : ShowMessageWithUser{
            override fun getUserListMessage(user: User) {
                sharedViewModel.setUser(user)
                findNavController().navigate(R.id.action_newMessageFragment_to_messageFragment)
            }
        })
        binding.adapterUserList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}