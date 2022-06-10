package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Conversation
import com.tomosia.chatapp.model.Message
import com.tomosia.chatapp.model.User

class ChatViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    private val conversationRef = db.collection("conversation")
    private val userRef = db.collection("user")
    private val createListConversationID = mutableListOf<String>()
    fun createMessage(user: User) {
        val users = hashMapOf(
            "listUser" to listOf(user.userID, checkCurrentUser()?.uid),
            "nameConversation" to user.userID
        )

        val message = hashMapOf(
            "idSend" to checkCurrentUser()!!.uid,
            "lastTime" to Timestamp.now(),
            "message" to "Hi, there"
        )

        // Check conversation
        userRef.document(checkCurrentUser()?.uid.toString()).get()
            .addOnSuccessListener { result ->
                val userData = result.toObject<User>()
                val listConversation = userData?.listConversation
                if (listConversation != null) {
                    for (i in 0..listConversation.size) {
                        val listConversationID = listConversation[i].id
                        createListConversationID.add(listConversationID)
                    }
                }
            }
            .addOnFailureListener { ex ->
                Log.d(TAG, "createMessage - get data fail: ${ex.message}")
            }
    }

    private fun addUser(users: HashMap<String, Any?>, message: Cloneable) {
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

    companion object {
        private const val TAG = "chatviewmodel"
    }
}