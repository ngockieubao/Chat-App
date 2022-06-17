package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentChatBinding
import com.tomosia.chatapp.model.Conversation
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment(), ChatInterface {
    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by viewModel()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        chatAdapter = ChatAdapter(this)
        lifecycleScope.launch {
            chatViewModel.checkCurrentUser()?.uid?.let { chatViewModel.readConversation(it) }
        }
        chatViewModel.conversation.observe(this.viewLifecycleOwner) {
            if (it == null) return@observe
            chatAdapter.listConversation = it as List<Conversation>
        }
        binding.rcvMessage.adapter = chatAdapter

        val fab: View = binding.fabChat
        fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_chatFragment_to_chatBottomSheetFragment)
        }

        return binding.root
    }

    override fun clickToChat(conversation: Conversation) {
        findNavController().navigate(ChatFragmentDirections.actionChatFragmentToMessageFragment(null, conversation))
    }

    companion object {
        private const val TAG = "ChatFragment"
    }
}