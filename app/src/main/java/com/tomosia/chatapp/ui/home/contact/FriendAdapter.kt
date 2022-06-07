package com.tomosia.chatapp.ui.home.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.databinding.RcvHomeContactBinding
import com.tomosia.chatapp.model.User

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    var listFriend = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class FriendViewHolder(private val rcvHomeContactBinding: RcvHomeContactBinding) : RecyclerView.ViewHolder
        (rcvHomeContactBinding.root) {

        fun bind(item: User?) {
            if (item == null) return
            rcvHomeContactBinding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            RcvHomeContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listFriend.size
    }
}