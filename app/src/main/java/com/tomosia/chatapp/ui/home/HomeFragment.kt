package com.tomosia.chatapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var dialog: SignOutDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        // Dialog log out
        dialog = SignOutDialog()
        binding.imgvSignoutHome.setOnClickListener {
            dialog.show(parentFragmentManager, "sign_out")
        }

        // Hide header & bottom navigation when navigate to conversation
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.messageFragment) {
                binding.linearHeaderHome.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.linearHeaderHome.visibility = View.VISIBLE
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}