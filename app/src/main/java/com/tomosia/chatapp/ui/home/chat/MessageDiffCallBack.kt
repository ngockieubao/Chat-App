package com.tomosia.chatapp.ui.home.chat

import androidx.recyclerview.widget.DiffUtil
import com.tomosia.chatapp.model.Message

class MessageDiffCallBack : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.lastTimeMessage == newItem.lastTimeMessage
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.lastTimeMessage == newItem.lastTimeMessage
    }
}