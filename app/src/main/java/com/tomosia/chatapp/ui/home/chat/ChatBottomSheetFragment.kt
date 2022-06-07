package com.tomosia.chatapp.ui.home.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.databinding.FragmentChatBottomSheetBinding

class ChatBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChatBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBottomSheetBinding.inflate(inflater, container, false)

        binding.tvCancelChatBottomSheet.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }
}