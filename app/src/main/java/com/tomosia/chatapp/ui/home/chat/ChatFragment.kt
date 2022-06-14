package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentChatBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by sharedViewModel()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        chatAdapter = ChatAdapter()
        lifecycleScope.launch {
            chatViewModel.readConversation()
        }
        chatViewModel.conversation.observe(this.viewLifecycleOwner) {
            chatAdapter.listConversation = it
        }
        binding.rcvMessage.adapter = chatAdapter

        chatViewModel.checkCurrentUser()
        val fab: View = binding.fabChat
        fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_chatFragment_to_chatBottomSheetFragment)
        }

        return binding.root
    }
}