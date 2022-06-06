package com.tomosia.chatapp.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomosia.chatapp.databinding.RcvHomeMessageBinding
import com.tomosia.chatapp.model.chat.Message

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val messageList = listOf<Message>()

    class HomeViewHolder(private val rcvMessageContentBinding: RcvHomeMessageBinding) : RecyclerView.ViewHolder(rcvMessageContentBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}