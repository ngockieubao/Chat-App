package com.tomosia.chatapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.ChatApplication
import com.tomosia.chatapp.model.LoginRegist
import com.tomosia.chatapp.util.TextUtils

class LoginRegistViewModel() : ViewModel() {
    private val context = ChatApplication.instant
    private val auth: FirebaseAuth = Firebase.auth

    private val _regist = MutableLiveData<LoginRegist>()
    val regist: LiveData<LoginRegist>
        get() = _regist

    private val _login = MutableLiveData<LoginRegist>()
    val login: LiveData<LoginRegist>
        get() = _login

    fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            //
        } else {
            //
        }
    }

    fun createAccount(email: String, passwd: String) {
        // Start create user
        if (TextUtils.isValidEmail(email)) {
            auth.createUserWithEmailAndPassword(email, passwd)
                .addOnSuccessListener {
                    val getRegist = LoginRegist(email, passwd)
                    _regist.value = getRegist
                }
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign-in success, update UI with user's info signed-in success
                        Log.d(TAG, "createAccount: success")
                        Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
                        // Update UI with user's info signed-in success
//                    val user = auth.currentUser
//                    updateUI(user)
                    }
                }.addOnFailureListener() { task ->
                    Log.d(TAG, "${task.message}")
                    Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Sign-in
    fun signIn(email: String, passwd: String) {
        if (TextUtils.isValidEmail(email)) {
            auth.signInWithEmailAndPassword(email, passwd)
                .addOnSuccessListener {
                    // Check email
                    if (email == auth.currentUser!!.email) {
                        val getLogin = LoginRegist(email, passwd)
                        _login.value = getLogin
                    }
                }
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signIn: success")
                        Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener() { exception ->
                    Log.d(TAG, exception.toString())
                    Toast.makeText(context, "The password is not correct", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        private const val TAG = "login_regist"
    }
}