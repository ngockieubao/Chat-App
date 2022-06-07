package com.tomosia.chatapp.model

data class Message(
    val avatar: String,
    val titleMessage: String,
    val contentMessage: String,
    val lastTimeMessage: Long
)
