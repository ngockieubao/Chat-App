package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.databinding.FragmentChatBottomSheetBinding
import com.tomosia.chatapp.model.User
import com.tomosia.chatapp.ui.home.contact.CreateMessageInterface
import com.tomosia.chatapp.ui.home.contact.ContactAdapter
import com.tomosia.chatapp.ui.home.contact.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateMessageBottomSheetFragment : BottomSheetDialogFragment(), CreateMessageInterface {
    private lateinit var binding: FragmentChatBottomSheetBinding

    // use viewModel instead of sharedViewModel
    private val contactViewModel: ContactViewModel by viewModel()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBottomSheetBinding.inflate(inflater, container, false)

        contactAdapter = ContactAdapter(this)
        binding.rcvMessageBottomSheet.adapter = contactAdapter
        contactViewModel.readListFriend()

        contactViewModel.friends.observe(this.viewLifecycleOwner) {
            contactAdapter.listFriend = it as List<User>
        }

        binding.tvCancelChatBottomSheet.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun clickToCreateMessage(user: User?) {
        findNavController().navigate(CreateMessageBottomSheetFragmentDirections.actionChatBottomSheetFragmentToMessageFragment(user, null))
    }
}