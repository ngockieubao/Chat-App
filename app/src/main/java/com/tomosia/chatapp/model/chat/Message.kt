package com.tomosia.chatapp.model.chat

data class Message(
    val avatar: String,
    val titleMessage: String,
    val contentMessage: String,
    val lastTimeMessage: Long
)
