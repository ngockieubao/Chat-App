package com.tomosia.chatapp.model.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.user.UserViewModel

class ChatViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val userViewModel: UserViewModel = UserViewModel()

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    fun addUserData() {
        val user = hashMapOf(
            "username" to "Ngoc",
            "email" to "ngockieu@gmail.com"
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "addUserData: failed", e)
            }
    }

    companion object {
        private const val TAG = "chatviewmodel"
    }
}