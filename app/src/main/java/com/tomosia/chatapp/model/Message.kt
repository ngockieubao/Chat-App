package com.tomosia.chatapp.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Message(
    val avatar: String?,
    val titleMessage: String,
    val contentMessage: String,
    val lastTimeMessage: Timestamp
) : Serializable {
    constructor() : this("", "", "", Timestamp.now()) {
        fun toHashMap(): HashMap<String, Any?> {
            return hashMapOf(
                "avatar" to avatar,
                "titleMessage" to titleMessage,
                "contentMessage" to contentMessage,
                "lastTimeMessage" to lastTimeMessage
            )
        }
    }
}
