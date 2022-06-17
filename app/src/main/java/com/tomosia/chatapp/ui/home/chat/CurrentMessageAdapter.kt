package com.tomosia.chatapp.ui.home.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.databinding.ChatCurrentUserBinding
import com.tomosia.chatapp.model.Message

class CurrentMessageAdapter : ListAdapter<Message, CurrentMessageAdapter.CurrentMessageViewHolder>(MessageDiffCallBack()) {
    class CurrentMessageViewHolder(private val chatCurrentUserBinding: ChatCurrentUserBinding) :
        RecyclerView.ViewHolder(chatCurrentUserBinding.root) {

        fun bind(item: Message?) {
            if (item == null) return
            chatCurrentUserBinding.tvChatCurrentUser.text = item.contentMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentMessageViewHolder {
        return CurrentMessageViewHolder(
            ChatCurrentUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrentMessageViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}