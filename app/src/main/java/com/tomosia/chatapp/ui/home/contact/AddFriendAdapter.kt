package com.tomosia.chatapp.ui.home.contact

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.R
import com.tomosia.chatapp.databinding.RcvHomeContactBottomSheetBinding
import com.tomosia.chatapp.model.User

class AddFriendAdapter(val addFriendInterface: AddFriendInterface) : RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {

    var listAddFriend = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class AddFriendViewHolder(private val rcvHomeContactBottomSheetBinding: RcvHomeContactBottomSheetBinding) :
        RecyclerView.ViewHolder(rcvHomeContactBottomSheetBinding.root) {

        fun bind(item: User?) {
            if (item == null) return
            rcvHomeContactBottomSheetBinding.item = item
            rcvHomeContactBottomSheetBinding.imageViewHomeContactBottomSheetAvatar.setImageResource(
                when (item.photoUrl) {
                    "default" -> R.drawable.ic_robot
                    else -> R.drawable.ic_robot
                }
            )

            rcvHomeContactBottomSheetBinding.imageViewAddFriend.setOnClickListener {
                addFriendInterface.clickToAddFriend(item)
                rcvHomeContactBottomSheetBinding.imageViewAddFriend.setImageResource(R.mipmap.ic_accept_64)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        return AddFriendViewHolder(
            RcvHomeContactBottomSheetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(listAddFriend[position])
    }

    override fun getItemCount(): Int {
        return listAddFriend.size
    }
}