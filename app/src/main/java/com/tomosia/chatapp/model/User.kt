package com.tomosia.chatapp.model

import com.google.firebase.firestore.DocumentReference
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
}
