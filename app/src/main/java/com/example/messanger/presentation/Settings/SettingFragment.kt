package com.example.messanger.presentation.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messanger.R
import com.example.messanger.databinding.FragmentNewMessageBinding
import com.example.messanger.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding?=null
    private val binding: FragmentSettingBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}