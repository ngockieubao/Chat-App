package com.tomosia.chatapp.model.user

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel : ViewModel() {

    private val auth = Firebase.auth.currentUser

    // Get user's profile
    private fun getUserProfile() {
        val user = auth
        user?.let {
            // Username, email, photoUrl
            val username = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            //
            val uid = user.uid
        }
    }
}