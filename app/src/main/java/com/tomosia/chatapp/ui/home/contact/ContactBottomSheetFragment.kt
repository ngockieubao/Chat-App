package com.tomosia.chatapp.ui.home.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.databinding.FragmentContactBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentContactBottomSheetBinding
    private val contactViewModel: ContactViewModel by sharedViewModel()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBottomSheetBinding.inflate(inflater, container, false)

        contactViewModel.readListUser()
        contactAdapter = ContactAdapter()
        contactViewModel.users.observe(this.viewLifecycleOwner) {
            if (it != null) {
                contactAdapter.listUser = it
                Log.d("ContactFragment", "onCreateView: ${it}")
            }
        }

        binding.rcvHomeContactBottomSheet.adapter = contactAdapter

        binding.tvDoneContactBottomSheet.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }
}