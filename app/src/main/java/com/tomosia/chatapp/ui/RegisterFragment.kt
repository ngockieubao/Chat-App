package com.tomosia.chatapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentRegisterBinding
import com.tomosia.chatapp.util.TextUtils

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        checkCurrentUser()
        binding.btnSignup.setOnClickListener {
            try {
                val email = binding.edtEmailRegister.text.toString()
                val passwd = binding.edtPasswdRegister.text.toString()
                createAccount(email, passwd)
            } catch (ex: IllegalArgumentException) {
                Toast.makeText(requireActivity(), "Fields is not empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvNavToLogin.setOnClickListener {
            navToLogIn()
        }

        return binding.root
    }

    // Checkout user accounts
    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            //
        } else {
            // Stay at register fragment
        }
    }

    // Create account
    private fun createAccount(email: String, passwd: String) {
        // Start create user
        if (TextUtils.isValidEmail(email)) {
            auth.createUserWithEmailAndPassword(email, passwd)
                .addOnSuccessListener {
                    navToHome()
                }
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign-in success, update UI with user's info signed-in success
                        Log.d(TAG, "createAccount: success")
                        Toast.makeText(requireActivity(), "Register success", Toast.LENGTH_SHORT).show()
                        // Update UI with user's info signed-in success
//                    val user = auth.currentUser
//                    updateUI(user)
                    }
                }.addOnFailureListener(requireActivity()) { task ->
                    Log.d(TAG, "${task.message}")
                    Toast.makeText(requireActivity(), "Register failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun navToLogIn() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun navToHome() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    companion object {
        private const val TAG = "register"
    }
}