package com.tomosia.chatapp.ui.home

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.databinding.FragmentHomeBinding
import com.tomosia.chatapp.model.chat.ChatViewModel
import com.tomosia.chatapp.model.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val loginRegistViewModel: LoginRegistViewModel by sharedViewModel()
    private val chatViewModel: ChatViewModel by sharedViewModel()

    private lateinit var dialog: SignOutDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        dialog = SignOutDialog()
        binding.imgvSignoutHome.setOnClickListener {
            dialog.show(parentFragmentManager, "sign_out")
        }

        binding.imgvContactsHome.setOnClickListener {
            binding.imgvContactsHome.setColorFilter(Color.BLUE)
            binding.imgvMessageHome.setColorFilter(Color.WHITE)

        }

        binding.imgvMessageHome.setOnClickListener {
            binding.imgvMessageHome.setColorFilter(Color.BLUE)
            binding.imgvContactsHome.setColorFilter(Color.WHITE)
        }

        chatViewModel.addMessageData()
        chatViewModel.readMessageData()

        return binding.root
    }

    companion object {
        private const val TAG = "home"
    }
}