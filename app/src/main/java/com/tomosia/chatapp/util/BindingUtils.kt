package com.tomosia.chatapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp

@BindingAdapter("setTime")
fun TextView.setTime(dt: Timestamp) {
    this.text = Utils.formatTime(context, dt)
}