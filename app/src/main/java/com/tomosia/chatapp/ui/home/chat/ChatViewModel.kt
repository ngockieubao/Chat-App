package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Message
import com.tomosia.chatapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    private val conversationRef = db.collection("conversation")
    private val userRef = db.collection("user")
    private val createListConversationID = mutableListOf<String>()


    suspend fun sendMessageTest() {
        val uid = "4h7cVWY6g1dsjVHn3ZQrfxiE22C2"
        val uidReceive = "ZYQhxffdKUW1MRhiK7W5uEm05Al1"

        val sendData = hashMapOf(
            "lastMessage" to "hello you!!!",
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf("ZYQhxffdKUW1MRhiK7W5uEm05Al1"),
            "name" to "conver"
        )

        val data = hashMapOf(
            "idSend" to "4h7cVWY6g1dsjVHn3ZQrfxiE22C2",
            "message" to "hello you!!!",
            "messageTime" to Timestamp.now(),
        )

        val update = hashMapOf(
            "listConversation" to FieldValue.arrayUnion()
        )

        val result = db.collection("conversation").add(sendData).await()
        val result2 =
            db.collection("conversation").document(result.id).collection("message").add(data)
                .await()
        val document = db.document("conversation/${result.id}")
        val result3 = db.collection("user").document("ZYQhxffdKUW1MRhiK7W5uEm05Al1")
            .update("listConversation", FieldValue.arrayUnion(document))
    }

    suspend fun sendMessage(idSender: String, idReceiver: String, idConversation: String?, message: String) {
        if (idConversation != null) {
            val conversation = hashMapOf(
                "lastMessage" to message,
                "lastMessageTime" to Timestamp.now(),
                "listUser" to arrayListOf(idSender, idReceiver),
                "name" to "name"
            )

            val message = hashMapOf(
                "idSend" to idSender,
                "message" to message,
                "messageTime" to Timestamp.now(),
            )

            try {
                val result = db.collection("conversation").add(conversation).await()
                val result2 =
                    db.collection("conversation").document(result.id).collection("message").add(message)
                        .await()
                val document = db.document("conversation/${result.id}")
                val result3 = db.collection("user").document(idSender)
                    .update("listConversation", FieldValue.arrayUnion(document))
                val result4 = db.collection("user").document(idReceiver)
                    .update("listConversation", FieldValue.arrayUnion(document))
            } catch (e: Exception) {

            }
        } else {
            createNewConversation(message, idSender, idReceiver)
        }
    }

    private suspend fun createNewConversation(
        message: String,
        idSender: String,
        idReceiver: String
    ) {
        val conversation = hashMapOf(
            "lastMessage" to message,
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf(idSender, idReceiver),
            "name" to "name"
        )

        val message = hashMapOf(
            "idSend" to idSender,
            "message" to message,
            "messageTime" to Timestamp.now(),
        )

        try {
            val result = db.collection("conversation").add(conversation).await()
            val result2 =
                db.collection("conversation").document(result.id).collection("message").add(message)
                    .await()
            val document = db.document("conversation/${result.id}")
            val result3 = db.collection("user").document(idSender)
                .update("listConversation", FieldValue.arrayUnion(document)).await()
            val result4 = db.collection("user").document(idReceiver)
                .update("listConversation", FieldValue.arrayUnion(document)).await()
        } catch (e: Exception) {

        }
    }


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