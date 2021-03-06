package com.tomosia.chatapp.ui.home.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.databinding.FragmentContactBottomSheetBinding
import com.tomosia.chatapp.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactBottomSheetFragment : BottomSheetDialogFragment(), AddFriendInterface {
    private lateinit var binding: FragmentContactBottomSheetBinding

    // use viewModel instead of sharedViewModel
    private val contactViewModel: ContactViewModel by viewModel()
    private lateinit var addFriendAdapter: AddFriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBottomSheetBinding.inflate(inflater, container, false)

        contactViewModel.readListAddFriend()
        addFriendAdapter = AddFriendAdapter(this)
        contactViewModel.users.observe(this.viewLifecycleOwner) {
            if (it == null) {
                Log.d(TAG, "onCreateView: null list")
                return@observe
            }
            addFriendAdapter.listAddFriend = it as List<User>
            Log.d(TAG, "onCreateView: list != null")
        }
        binding.rcvHomeContactBottomSheet.adapter = addFriendAdapter

        binding.tvDoneContactBottomSheet.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun clickToAddFriend(user: User?) {
        Toast.makeText(requireActivity(), "Sent add friend", Toast.LENGTH_SHORT).show()
        contactViewModel.addFriend(user)
    }

    companion object {
        private const val TAG = "contactbtmsheet"
    }
}