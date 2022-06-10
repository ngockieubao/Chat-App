package com.tomosia.chatapp.model

data class Conversation(
    val listOfConversation: List<String>
) {
    constructor() : this(
        emptyList()
    )
}