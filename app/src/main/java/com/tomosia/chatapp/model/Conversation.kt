package com.tomosia.chatapp.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Conversation(
    val avatar: String?,
    val lastMessage: String?,
    val lastMessageTime: Timestamp,
    val listUser: List<String>,
    val nameConversation: String
) : Serializable {
    constructor() : this(
        "",
        "",
        Timestamp.now(),
        emptyList(),
        ""
    )
}