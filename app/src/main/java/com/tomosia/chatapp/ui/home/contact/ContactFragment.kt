package com.tomosia.chatapp.ui.home.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentContactBinding
import com.tomosia.chatapp.model.User
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactFragment : Fragment(), CreateMessageInterface {
    private lateinit var binding: FragmentContactBinding
    private val contactViewModel: ContactViewModel by sharedViewModel()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false)

        contactViewModel.readListFriend()
        contactAdapter = ContactAdapter(this)
        contactViewModel.friends.observe(this.viewLifecycleOwner) {
            contactAdapter.listFriend = it
        }

        binding.rcvHomeContact.adapter = contactAdapter

        val fab: View = binding.fabContact
        fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_contactFragment_to_contactBottomSheetFragment)
        }

        return binding.root
    }

    override fun clickToCreateMessage(user: User) {
    }
}