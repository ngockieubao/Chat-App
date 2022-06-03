package com.tomosia.chatapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    // Init auth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        // Declare auth
        auth = Firebase.auth

        // Navigate
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        return binding.root
    }

    // on start check current user
    override fun onStart() {
        super.onStart()
        // check cur user
        val currentUser = auth.currentUser
        if (currentUser != null)
//            login()
        else {
            // stay at welcome fragment
        }
    }

    private fun login() {
        findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
    }
}