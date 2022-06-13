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
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Message
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

    suspend fun sendMessage(idSender: String, idReceiver: String, idConversation: String, message: String) {
//        if (idConversation != null) {
//            val conversation = hashMapOf(
//                "lastMessage" to message,
//                "lastMessageTime" to Timestamp.now(),
//                "listUser" to arrayListOf(idSender, idReceiver)
//            )
//
//            val message = hashMapOf(
//                "idSend" to idSender,
//                "message" to message,
//                "messageTime" to Timestamp.now(),
//            )
//
//            try {
//                val result = db.collection("conversation").add(conversation).await()
//                val result2 =
//                    db.collection("conversation").document(result.id).collection("message").add(message)
//                        .await()
//                val document = db.document("conversation/${result.id}")
//                val result3 = db.collection("user").document(idSender)
//                    .update("listConversation", FieldValue.arrayUnion(document))
//                val result4 = db.collection("user").document(idReceiver)
//                    .update("listConversation", FieldValue.arrayUnion(document))
//            } catch (e: Exception) {
//
//            }
//        } else {
        createNewConversation(idSender, idReceiver, idConversation, message)
//        }
    }

    private suspend fun createNewConversation(
        idSender: String,
        idReceiver: String,
        idConversation: String,
        message: String
    ) {
        val conversation = hashMapOf(
            "lastMessage" to message,
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf(idSender, idReceiver),
            "idConversation" to idConversation
        )

        val message = hashMapOf(
            "idSend" to idSender,
            "message" to message,
            "messageTime" to Timestamp.now()
        )

        try {
            // Add conversation
            val result = db.collection("conversation")
                .add(conversation)
                .await()
            Log.d(TAG, ": Add conversation")

            // Add message
            val result2 =
                db.collection("conversation")
                    .document(result.id)
                    .collection("message")
                    .add(message)
                    .await()
            Log.d(TAG, ": Add message")

            //
            val document = db.document("conversation/${result.id}")
            Log.d(TAG, ": asdasd")

            // Update id sender
            val result3 =
                db.collection("user")
                    .document(idSender)
                    .update("listConversation", FieldValue.arrayUnion(document))
                    .await()
            Log.d(TAG, ": Update id sender")

            // Update id receiver
            val result4 =
                db.collection("user")
                    .document(idReceiver)
                    .update("listConversation", FieldValue.arrayUnion(document))
                    .await()
            Log.d(TAG, ": Update id receiver")

        } catch (ex: Exception) {
            Log.d(TAG, "createNewConversation: ${ex.message} -bug")
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

    companion object {
        private const val TAG = "ChatViewModel"
    }
}