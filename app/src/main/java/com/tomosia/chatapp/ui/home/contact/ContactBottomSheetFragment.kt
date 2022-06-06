package com.tomosia.chatapp.ui.home.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tomosia.chatapp.databinding.FragmentContactBottomSheetBinding

class ContactBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentContactBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }
}