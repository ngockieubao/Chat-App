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
    private lateinit var addFriendAdapter: AddFriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBottomSheetBinding.inflate(inflater, container, false)

        contactViewModel.readListUser()
        addFriendAdapter = AddFriendAdapter()
        contactViewModel.users.observe(this.viewLifecycleOwner) {
            if (it != null) {
                addFriendAdapter.listUser = it
                Log.d("ContactBottomSheet", "onCreateView: $it")
            }
        }

        binding.rcvHomeContactBottomSheet.adapter = addFriendAdapter

        binding.tvDoneContactBottomSheet.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }
}