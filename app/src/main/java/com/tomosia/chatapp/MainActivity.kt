package com.tomosia.chatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tomosia.chatapp.databinding.ActivityMainBinding
import com.tomosia.chatapp.ui.home.chat.ChatViewModel
import com.tomosia.chatapp.ui.home.contact.ContactViewModel
import com.tomosia.chatapp.ui.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val contactViewModel: ContactViewModel by viewModel<ContactViewModel>()
    private val chatViewModel: ChatViewModel by viewModel<ChatViewModel>()
    private val loginRegistViewModel: LoginRegistViewModel by viewModel<LoginRegistViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.actNavHost)
        return navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()

//        val navController = this.findNavController(R.id.actNavHost)
//        if (!navController.popBackStack()) {
//            // Call finish() on your Activity
//            finish()
//        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}