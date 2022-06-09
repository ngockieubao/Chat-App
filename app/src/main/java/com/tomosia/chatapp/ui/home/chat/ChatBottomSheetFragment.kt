package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentChatBottomSheetBinding
import com.tomosia.chatapp.ui.home.contact.ContactAdapter
import com.tomosia.chatapp.ui.home.contact.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatBottomSheetFragment : BottomSheetDialogFragment(), ChatInterface {
    private lateinit var binding: FragmentChatBottomSheetBinding
    private val contactViewModel: ContactViewModel by sharedViewModel()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBottomSheetBinding.inflate(inflater, container, false)

        contactViewModel.readListFriend()
        contactAdapter = ContactAdapter(this)
        contactViewModel.friends.observe(this.viewLifecycleOwner) {
            contactAdapter.listFriend = it
        }

        binding.rcvMessageBottomSheet.adapter = contactAdapter

        binding.tvCancelChatBottomSheet.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }

    override fun createMessage() {
        findNavController().navigate(R.id.action_chatBottomSheetFragment_to_messageFragment)
        Toast.makeText(requireActivity(), "Clicked to create message", Toast.LENGTH_SHORT).show()
    }
}