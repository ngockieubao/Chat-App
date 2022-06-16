package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentMessageBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val chatViewModel: ChatViewModel by viewModel()
    private val argsUser by navArgs<MessageFragmentArgs>()
    private val argsConversation by navArgs<MessageFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        var message: String? = null

        binding.imageViewMessageBack.setOnClickListener {
            findNavController().navigate(R.id.action_messageFragment_to_chatFragment)
        }

        val bundleConversationChoose = argsConversation.conversation
        val bundleUserChoose = argsUser.user
        binding.tvChatUsername.text = bundleUserChoose?.username

        binding.edtTextMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null)
                    message = s.toString()
            }
        })

        // Conversation
        lifecycleScope.launch {
            chatViewModel.checkCurrentUser()?.let { idSender ->
                chatViewModel.checkConversation(
                    idSender.uid,
                    "${bundleUserChoose?.userID}",
                    "unknown create"
                )
            }
        }

        // Send message
        binding.imageViewSendMessage.setOnClickListener {
            lifecycleScope.launch {
                chatViewModel.checkCurrentUser()?.uid?.let { idSender ->
                    chatViewModel.sendMessage(
                        idSender,
                        "${bundleUserChoose?.userID}",
                        message,
                        // username sent last message
                        "unknown send"
                    )
                }
            }
            binding.edtTextMessage.text.clear()
        }


        return binding.root
    }

    companion object {
        private const val TAG = "MessageFragment"
    }
}