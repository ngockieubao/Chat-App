package com.tomosia.chatapp.util

import android.content.Context
import com.google.firebase.Timestamp
import com.tomosia.chatapp.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatTime(context: Context, time: Timestamp): String {
        val timeFormat = SimpleDateFormat(context.getString(R.string.fm_time), Locale.getDefault())
        return timeFormat.format(time)

        val date = Date(time * 1000)
        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        sfd.format(date)

        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        sfd.format(Date(time))

        val timestamp = it["time"] as com.google.firebase.Timestamp
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()
    }
}