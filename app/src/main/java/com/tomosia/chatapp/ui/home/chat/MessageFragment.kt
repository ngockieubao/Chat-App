package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentMessageBinding
import com.tomosia.chatapp.model.User
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val chatViewModel: ChatViewModel by sharedViewModel()

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

        val bundleUserChoose = arguments?.getSerializable("userChoose") as User
        binding.tvChatUsername.text = bundleUserChoose.username

        binding.edtTextMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null)
                    message = s.toString()
            }
        })

        //
        lifecycleScope.launch {
            chatViewModel.checkCurrentUser()?.let {
                chatViewModel.checkConversation(
                    it.uid,
                    "${bundleUserChoose.userID}"
                )
            }
        }

        binding.imageViewSendMessage.setOnClickListener {
//            val bundle = bundleOf("sendMessage" to message)
//            findNavController().navigate(R.id.action_messageFragment_to_chatBottomSheetFragment, bundle)
            lifecycleScope.launch {
                chatViewModel.checkCurrentUser()?.uid?.let { it1 ->
                    chatViewModel.sendMessage(
                        it1,
                        "${bundleUserChoose.userID}",
                        message
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