package com.tomosia.chatapp.util

import android.util.Patterns

object TextUtils {
    fun isValidEmail(email: String): Boolean {
        return !TextUtils().isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}