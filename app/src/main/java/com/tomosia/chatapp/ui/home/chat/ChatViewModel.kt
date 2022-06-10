package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Message

class ChatViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    private val conversationRef = db.collection("conversation")
    fun createMessage() {
        val users = hashMapOf(
            "listUser" to listOf("4h7cVWY6g1dsjVHn3ZQrfxiE22C2", "ZYQhxffdKUW1MRhiK7W5uEm05Al1"),
            "nameConversation" to ""
        )

        val message = hashMapOf(
            "idSend" to checkCurrentUser()!!.uid,
            "lastTime" to Timestamp.now(),
            "titleMessage" to "Thanh Vu",
            "message" to "Hi, there"
        )

        // Check conversation
        conversationRef.add(users)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                conversationRef.document(documentReference.id).collection("message").add(message)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "addMessageData: failed", e)
            }
    }

    fun updateMessage() {
        conversationRef.document("fUgmLbvjmg3tvQMtDIKz")
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

//    fun readCurrentUser() {
//        db.collection("user").document(checkCurrentUser()!!.uid).get()
//            .addOnSuccessListener { result ->
//                Log.d(TAG, "readUserData: ${result.data} <= ${result.toObject<com.tomosia.chatapp.model.User>()}")
//                val listResult = result.toObject<com.tomosia.chatapp.model.User>()
//                if (listResult != null) {
//                    Log.d(TAG, "readUserData: ${listResult.listConversation[0].id}")
//                    try {
//                        val listCon = listResult.listConversation[0].id
//                        db.collection("conversation").document(listCon).get()
//                            .addOnSuccessListener { result ->
//                                Log.d(TAG, "readUserData: ${result.data}")
//                            }
//                            .addOnFailureListener { exception ->
//                                Log.d(TAG, "readUserData: ${exception.message}")
//                            }
//                    } catch (ex: IndexOutOfBoundsException) {
//                        Log.d(TAG, "readUserData: ${ex.message}")
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "readUserData: read data failed")
//            }
//    }

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

    companion object {
        private const val TAG = "chatviewmodel"
    }
}