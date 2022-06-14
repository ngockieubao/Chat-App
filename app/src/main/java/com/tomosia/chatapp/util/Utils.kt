package com.tomosia.chatapp.util

import android.content.Context
import com.google.firebase.Timestamp
import com.tomosia.chatapp.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatTime(context: Context, time: Timestamp): String {
        val date = Date(time.seconds.times(1000))
        val sfd = SimpleDateFormat(context.getString(R.string.fm_time), Locale.getDefault())
        return sfd.format(date)
    }
}