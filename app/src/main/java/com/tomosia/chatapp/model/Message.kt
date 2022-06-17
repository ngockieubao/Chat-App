package com.tomosia.chatapp.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Message(
    val avatar: String?,
    val idSender: String,
    val contentMessage: String,
    val lastTimeMessage: Timestamp?
) : Serializable {
    constructor() : this(
        "", "", "", null
    ) {
        fun toHashMap(): HashMap<String, Any?> {
            return hashMapOf(
                "avatar" to avatar,
                "idSender" to idSender,
                "contentMessage" to contentMessage,
                "lastTimeMessage" to lastTimeMessage
            )
        }
    }
}
