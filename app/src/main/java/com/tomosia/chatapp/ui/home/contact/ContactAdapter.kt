package com.tomosia.chatapp.ui.home.contact

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.RcvHomeContactBinding
import com.tomosia.chatapp.model.User
import com.tomosia.chatapp.ui.home.chat.ChatInterface

class ContactAdapter(val chatInterface: ChatInterface) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var listFriend = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ContactViewHolder(private val rcvHomeContactBinding: RcvHomeContactBinding) : RecyclerView.ViewHolder
        (rcvHomeContactBinding.root) {

        fun bind(item: User?) {
            if (item == null) return
            rcvHomeContactBinding.item = item
            rcvHomeContactBinding.imageViewHomeContactAvatar.setImageResource(
                when (item.photoUrl) {
                    "default" -> R.drawable.ic_robot
                    else -> R.drawable.ic_robot
                }
            )
            rcvHomeContactBinding.lnUserInformation.setOnClickListener {
                chatInterface.createMessage()
                Log.d(TAG, "create message")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            RcvHomeContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(listFriend[position])
    }

    override fun getItemCount(): Int {
        return listFriend.size
    }

    companion object {
        private const val TAG = "ContactAdapter"
    }
}