package com.tomosia.chatapp.ui.home.chat

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.RcvHomeMessageBinding
import com.tomosia.chatapp.model.Message

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    var listMessage = listOf<Message>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ChatViewHolder(private val rcvHomeMessageBinding: RcvHomeMessageBinding) :
        RecyclerView.ViewHolder(rcvHomeMessageBinding.root) {

        fun bind(item: Message?) {
            if (item == null) return
            rcvHomeMessageBinding.item = item
            rcvHomeMessageBinding.imageViewHomeMessageAvatar.setImageResource(
                when (item.avatar) {
                    "default" -> R.drawable.ic_robot
                    else -> R.drawable.ic_robot
                }
            )

            rcvHomeMessageBinding.lnMessage.setOnClickListener {
                Log.d(TAG, "created message")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            RcvHomeMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(listMessage[position])
    }

    override fun getItemCount(): Int {
        return listMessage.size
    }

    companion object{
        private const val TAG = "ChatAdapter"
    }
}