package com.tomosia.chatapp.model

import com.google.firebase.firestore.DocumentReference

data class User(
    val userID: String,
    val email: String,
    val username: String,
    val photoUrl: String,
    val listConversation: List<DocumentReference>
) {
    constructor() : this("", "", "", "", emptyList())
}
