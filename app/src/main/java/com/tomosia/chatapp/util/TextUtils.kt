package com.tomosia.chatapp.util

object TextUtils {
    fun isValidEmail(email: String): Boolean {
        return Constants.EMAIL_ADDRESS_PATTERN!!.matcher(email).matches()
    }
}
