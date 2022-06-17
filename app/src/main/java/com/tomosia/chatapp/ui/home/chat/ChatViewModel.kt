package com.tomosia.chatapp.ui.home.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.Conversation
import com.tomosia.chatapp.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel(), ChatInterface {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val _message = MutableLiveData<List<Message>>()
    val message: LiveData<List<Message>>
        get() = _message

    private val _conversation = MutableLiveData<List<Conversation?>>()
    val conversation: LiveData<List<Conversation?>>
        get() = _conversation

    private lateinit var idDocument: String
    private val conRef = db.collection("conversation")

    suspend fun sendMessage(
        idSender: String,
        idReceiver: String,
        message: String?,
        nameConversation: String
    ) {
        val conversation = hashMapOf(
            "lastMessage" to message,
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf(idSender, idReceiver),
            "nameConversation" to nameConversation
        )

        val message = hashMapOf(
            "idSend" to idSender,
            "message" to message,
            "messageTime" to Timestamp.now(),
        )
        try {
            val result = conRef.document(idDocument).set(conversation).await()
            val result2 =
                conRef.document(idDocument).collection("message")
                    .add(message).await()
        } catch (ex: Exception) {
            Log.d(TAG, "sendMessage: ${ex.message}")
        }
    }

    private suspend fun createNewConversation(
        idSender: String,
        idReceiver: String,
        message: String,
        nameConversation: String
    ) {
        val conversation = hashMapOf(
            "lastMessage" to message,
            "lastMessageTime" to Timestamp.now(),
            "listUser" to arrayListOf(idSender, idReceiver),
            "nameConversation" to nameConversation
        )

        val message = hashMapOf(
            "idSend" to idSender,
            "message" to message,
            "messageTime" to Timestamp.now()
        )

        try {
            // Add conversation
            val result = conRef
                .add(conversation)
                .await()

            // Add message
            val result2 =
                conRef
                    .document(result.id)
                    .collection("message")
                    .add(message)
                    .await()

        } catch (ex: Exception) {
            Log.d(TAG, "createNewConversation: ${ex.message}")
        }
    }

    suspend fun checkConversation(
        idSender: String,
        idReceiver: String,
        nameConversation: String
    ) {
        // List snapshot document(conversation)
        val query =
            // check conversation existed
            conRef.whereArrayContainsAny(
                "listUser",
                listOf(idSender, idReceiver)
            ).get().await()
        if (query.documents.isNotEmpty()) {
            // get document conversation
            val doc = query.documents
            for (item in doc) {
                idDocument = (item as QueryDocumentSnapshot).id
            }
            val queryToObject = query.toObjects<Conversation>()
            for (i in queryToObject) {
                if (i.listUser.contains(idSender) && i.listUser.contains(idReceiver)
                ) {
                    // TODO nav to conversation
                    clickToChat(i)
//                    sendMessage(idSender, idReceiver, null, "unknown")
                }
            }
        } else
            createNewConversation(idSender, idReceiver, "This is first message", nameConversation)
    }

    suspend fun readConversation(
        idSender: String
    ) {
        // filter snapshot document(conversation) of current user
        val query =
            conRef.whereArrayContains(
                "listUser",
                idSender
            ).get().await()
        if (query.documents.isNotEmpty()) {
            // get document conversation
            val doc = query.documents
            for (item in doc) {
                idDocument = (item as QueryDocumentSnapshot).id
            }
            val queryToObject = query.toObjects<Conversation>()
            for (i in queryToObject) {
                if ((i.listUser.contains(idSender))
                ) {
                    _conversation.value = queryToObject
                }
            }
        }
    }

    suspend fun readMessage(
        idSender: String
    ) {
        // filter snapshot document(conversation) of current user
        val queryConversation =
            conRef.whereArrayContains(
                "listUser",
                idSender
            ).get().await()
        if (queryConversation.documents.isNotEmpty()) {
            // get document conversation
            val doc = queryConversation.documents
            for (item in doc) {
                idDocument = (item as QueryDocumentSnapshot).id
            }
            val queryMessage = conRef.document(idDocument).collection("message").get().await()
            if (queryMessage.documents.isNotEmpty()) {
                // get document conversation
                val doc = queryMessage.documents
                for (i in doc) {
                    // document-id message
                    val abc = (i as QueryDocumentSnapshot).id
                    Log.d(TAG, "readMessage - list: $abc")

                }
            }
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

    // Sign-out
    fun signOut() {
        Firebase.auth.signOut()
        _conversation.value = null
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }

    override fun clickToChat(conversation: Conversation) {
    }
}