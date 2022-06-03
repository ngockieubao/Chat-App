package com.tomosia.chatapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    // Check current user
    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }

    // Get user's profile
    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Username, email, photoUrl
            val username = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified
        }
    }
}