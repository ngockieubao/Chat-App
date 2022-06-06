package com.tomosia.chatapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
    private lateinit var binding: FragmentContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false)

        val fab: View = binding.fabContact
        fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_contactFragment_to_contactBottomSheetFragment)
        }

        return binding.root
    }
}