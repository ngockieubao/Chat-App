package com.tomosia.chatapp.model

import com.google.firebase.firestore.DocumentReference
import com.tomosia.chatapp.util.Constants
import java.io.Serializable

data class User(
    val userID: String?,
    val email: String?,
    val username: String?,
    val photoUrl: String?,
    val listFriend: List<String>?,
    val listConversation: List<DocumentReference>?
) : Serializable {
    constructor() : this("", "", "", "", emptyList(), emptyList())

    fun toHashMap(): HashMap<String, Any?>{
        return hashMapOf(
            "userID" to userID,
            "email" to email,
            "username" to username,
            "photoUrl" to photoUrl,
            "listFriend" to listFriend,
            "listConversation" to listConversation
        )
    }
}
