package com.tomosia.chatapp.fragment

import android.os.Bundle
import android.text.TextUtils
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
import com.tomosia.chatapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        //
        auth = Firebase.auth

        //
        binding.btnSignin.setOnClickListener {
            try{
                val getEmail = binding.edtNameLogin.text.toString()
                val getPasswd = binding.edtPasswdLogin.text.toString()
                signIn(getEmail, getPasswd)
            }catch (ex: IllegalArgumentException){
                Toast.makeText(requireActivity(), "Fields is not empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate
        binding.tvNavToRegister.setOnClickListener {
            navToSignUp()
        }

        return binding.root
    }

    // Sign-in
    private fun signIn(email: String, passwd: String) {
        // TODO check email & passwd
        // first check is empty
        // next check equals

//        if(email == auth.currentUser?.email && passwd.is)

        auth.signInWithEmailAndPassword(email, passwd)
            .addOnCompleteListener(requireActivity()) { task ->
                Log.d(TAG, "signIn: success")
                Toast.makeText(requireActivity(), "Login success", Toast.LENGTH_SHORT).show()
                navToHome()
            }.addOnFailureListener(requireActivity()) { task ->
                Log.d(TAG, "signIn: failed")
                Toast.makeText(requireActivity(), "Login failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navToHome(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navToSignUp(){
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        Toast.makeText(requireActivity(), "hmm", Toast.LENGTH_SHORT).show()

    }

    // TODO forgot password
    // use email verify

    companion object {
        private const val TAG = "login"
    }
}