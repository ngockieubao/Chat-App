package com.tomosia.chatapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentRegisterBinding
import com.tomosia.chatapp.model.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val loginRegistViewModel: LoginRegistViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        var email: String? = null
        var passwd: String? = null

        binding.edtEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    email = s.toString()
                } else
                    Toast.makeText(requireActivity(), "Email is not empty", Toast.LENGTH_SHORT).show()
            }
        })

        binding.edtPasswdRegister.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    passwd = s.toString()
                } else
                    Toast.makeText(requireActivity(), "Password is not empty", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSignup.setOnClickListener {
            loginRegistViewModel.createAccount(email!!, passwd!!)
            Log.d(TAG, "email: ${loginRegistViewModel.regist.value?.email} - passwd: ${loginRegistViewModel.regist.value?.passwd} ")
        }

        loginRegistViewModel.regist.observe(this.viewLifecycleOwner) {
            if (it != null)
                Log.d(TAG, "$it")
            navToHome()
        }

        binding.tvNavToLogin.setOnClickListener {
            navToLogIn()
        }

        return binding.root
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