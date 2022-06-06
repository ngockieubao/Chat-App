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

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    fun addMessageData() {
        val message = hashMapOf(
            "titleMessage" to "Thanh Vu",
            "contentMessage" to "Hi, guy",
            "lastTimeMessage" to 12500
        )

        db.collection("messages")
            .add(message)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "addMessageData: failed", e)
            }
    }

    fun readMessageData() {
        db.collection("messages").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    companion object {
        private const val TAG = "chatviewmodel"
    }
}