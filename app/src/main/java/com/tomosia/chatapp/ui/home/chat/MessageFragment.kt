package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentMessageBinding
import com.tomosia.chatapp.model.User

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(inflater, container, false)

        binding.imageViewMessageBack.setOnClickListener {
            findNavController().navigate(R.id.action_messageFragment_to_chatFragment)
        }

        val bundleUserChoose = arguments?.getSerializable("userChoose") as User
        binding.tvChatUsername.text = bundleUserChoose.username

        return binding.root
    }

    companion object {
        private const val TAG = "MessageFragment"
    }
}