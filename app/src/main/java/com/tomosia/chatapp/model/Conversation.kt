package com.tomosia.chatapp.model

import com.google.firebase.Timestamp

data class Conversation(
    val lastMessage: String,
    val lastMessageTime: Timestamp,
    val listUser: List<String>
) {
    constructor() : this(
        "",
        Timestamp.now(),
        emptyList()
    )
}