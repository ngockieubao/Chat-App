package com.tomosia.chatapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val loginRegistViewModel: LoginRegistViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        var email: String? = null
        var passwd: String? = null

        binding.edtEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null)
                    email = s.toString()
            }
        })

        binding.edtPasswdLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null)
                    passwd = s.toString()
            }
        })

        //
        binding.btnSignin.setOnClickListener {
            loginRegistViewModel.signIn(email, passwd)
        }

        loginRegistViewModel.login.observe(this.viewLifecycleOwner) {
            if (it == null) return@observe
            Log.d(TAG, "$it")
            navToHome()
        }

        // Navigate
        binding.tvNavToRegister.setOnClickListener {
            navToSignUp()
        }

        return binding.root
    }

    private fun navToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navToSignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

// TODO forgot password
// use email verify

    companion object {
        private const val TAG = "login"
    }
}