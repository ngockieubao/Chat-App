package com.tomosia.chatapp.ui.home.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.databinding.ChatOtherUserBinding
import com.tomosia.chatapp.model.Message

class FriendMessageAdapter : ListAdapter<Message, FriendMessageAdapter.FriendMessageViewHolder>(MessageDiffCallBack()) {
    class FriendMessageViewHolder(private val chatOtherUserBinding: ChatOtherUserBinding) : RecyclerView.ViewHolder(chatOtherUserBinding.root) {
        fun bind(item: Message?) {
            if (item == null) return

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendMessageViewHolder {
        return FriendMessageViewHolder(
            ChatOtherUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendMessageViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}