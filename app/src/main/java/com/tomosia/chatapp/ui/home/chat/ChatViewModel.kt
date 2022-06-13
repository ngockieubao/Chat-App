package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Conversation
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

    suspend fun sendMessage(
        idSender: String,
        idReceiver: String,
        idConversation: String?,
        message: String
    ) {
        if (idConversation != null) {
            val conversation = hashMapOf(
                "lastMessage" to message,
                "lastMessageTime" to Timestamp.now(),
                "listUser" to arrayListOf(idSender, idReceiver)
            )

            val message = hashMapOf(
                "idSend" to idSender,
                "message" to message,
                "messageTime" to Timestamp.now(),
            )
            try {
                val result = db.collection("conversation").document(idConversation).set(conversation).await()
                val result2 =
                    db.collection("conversation").document(idConversation).collection("message").add(message)
                        .await()
            } catch (ex: Exception) {
                Log.d(TAG, "sendMessage bb: ${ex.message}")
            }
        } else {
            createNewConversation(idSender, idReceiver, message)
        }
    }

    suspend fun checkConversation(
        idCurrentUser: String,
        idListUser: List<DocumentReference>?,
        idSender: String,
        idReceiver: String,
        idConversation: String?,
        message: String
    ) {
        if (idListUser != null) {
            val us = db.collection("user").document(checkCurrentUser()!!.uid).get().await()
            val us1 = us.toObject<User>()
            Log.d(TAG, "checkConversation:$us ---- $us1 ---- ${us1?.listConversation}")
//            for (i in 0..idListUser.size.minus(1)) {
//                for (j in ob){
//                    if () {
//                        sendMessage(idSender, idReceiver, idConversation, message)
//                    } else
//                        createNewConversation(idSender, idReceiver, message)
//                }
//            }
        }
    }

    private suspend fun createNewConversation(
        idSender: String,
        idReceiver: String,
        message: String
    ) {
        val conversation = hashMapOf(
            "lastMessage" to message,
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf(idSender, idReceiver)
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

            // Add message
            val result2 =
                db.collection("conversation")
                    .document(result.id)
                    .collection("message")
                    .add(message)
                    .await()

            //
            val document = db.document("conversation/${result.id}")

            // Update id sender
            val result3 =
                db.collection("user")
                    .document(idSender)
                    .update("listConversation", FieldValue.arrayUnion(document))
                    .await()

            // Update id receiver
            val result4 =
                db.collection("user")
                    .document(idReceiver)
                    .update("listConversation", FieldValue.arrayUnion(document))
                    .await()

        } catch (ex: Exception) {
            Log.d(TAG, "createNewConversation: ${ex.message}")
        }
    }

    suspend fun parseListConversation(test: List<DocumentReference>?) {
        val ob = db.collection("user").document(checkCurrentUser()!!.uid).get().await()
        val ob1 = ob.toObject<User>()
        if (ob1 != null) {

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