package com.tomosia.chatapp.ui.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.ui.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.IllegalStateException

class SignOutDialog : DialogFragment() {
    private val loginRegistViewModel: LoginRegistViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure to log out?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                    loginRegistViewModel.signOut()
                    navToWelcome()
                    Toast.makeText(requireActivity(), "Signed out", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    //
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun navToWelcome() {
        findNavController().navigate(R.id.action_homeFragment_to_welcomeFragment)
    }
}