package com.tomosia.chatapp.ui.home.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.RcvHomeContactBinding
import com.tomosia.chatapp.model.User

class AddFriendAdapter : RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {

    var listUser = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class AddFriendViewHolder(private val rcvHomeContactBinding: RcvHomeContactBinding) :
        RecyclerView.ViewHolder(rcvHomeContactBinding.root) {

        fun bind(item: User?) {
            if (item == null) return
            rcvHomeContactBinding.item = item
            rcvHomeContactBinding.imageViewHomeContactAvatar.setImageResource(
                when (item.photoUrl) {
                    "default" -> R.drawable.ic_robot
                    else -> R.drawable.ic_robot
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        return AddFriendViewHolder(
            RcvHomeContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}