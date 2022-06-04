package com.tomosia.chatapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.databinding.FragmentHomeBinding
import com.tomosia.chatapp.model.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val loginRegistViewModel: LoginRegistViewModel by sharedViewModel()

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

        return binding.root
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

    companion object {
        private const val TAG = "home"
    }
}