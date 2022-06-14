package com.tomosia.chatapp.ui.home.chat

import com.tomosia.chatapp.model.Conversation

interface ChatInterface {
    fun clickToChat(conversation: Conversation)
}