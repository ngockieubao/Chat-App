package com.tomosia.chatapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.tomosia.chatapp.databinding.FragmentChatBinding
import com.tomosia.chatapp.model.chat.ChatViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private val chatViewModel: ChatViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        chatViewModel.addMessageData()
        chatViewModel.readMessageData()

//        binding.fabChat.setOnClickListener {
//            Toast.makeText(requireActivity(), "clicked", Toast.LENGTH_SHORT).show()
//        }

        val fab: View = binding.fabChat
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Here's a message", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        return binding.root
    }
}