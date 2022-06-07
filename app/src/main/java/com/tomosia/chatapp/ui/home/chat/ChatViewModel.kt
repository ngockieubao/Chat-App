package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Message
import com.tomosia.chatapp.model.User

class ChatViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>>
        get() = _user


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

    fun addUserData() {
        val user = hashMapOf(
            "userID" to checkCurrentUser()!!.uid,
            "email" to checkCurrentUser()!!.email,
            "username" to checkCurrentUser()!!.uid,
            "photoUrl" to "default"
        )
        db.collection("user")
            .document(checkCurrentUser()!!.uid).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: success")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "addUserData: failed")
            }
    }

    fun readUserData() {
        db.collection("user").document(checkCurrentUser()!!.uid).get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "readUserData: ${result.data} <= ${result.toObject<User>()}")
                val listResult = result.toObject<User>()
                if (listResult != null) {
                    Log.d(TAG, "readUserData: ${listResult.listConversation[0].id}")
                    try {
                        val listCon = listResult.listConversation[0].id
                        db.collection("conversation").document(listCon).get()
                            .addOnSuccessListener { result ->
                                Log.d(TAG, "readUserData: ${result.data}")
                            }
                            .addOnFailureListener { exception ->
                                Log.d(TAG, "readUserData: ${exception.message}")
                            }
                    } catch (ex: IndexOutOfBoundsException) {
                        Log.d(TAG, "readUserData: ${ex.message}")
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "readUserData: read data failed")
            }
    }

    fun checkCurrentUser(): FirebaseUser? {
        val user = auth
        if (user != null) {
            // user is signed in
            return user
        } else {
            // no user is signed in
            return null
        }
    }

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

    companion object {
        private const val TAG = "chatviewmodel"
    }
}