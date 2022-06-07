package com.tomosia.chatapp.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.ChatApplication
import com.tomosia.chatapp.model.LoginRegist
import com.tomosia.chatapp.util.TextUtils

class LoginRegistViewModel() : ViewModel() {
    private val context = ChatApplication.instant
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private val _regist = MutableLiveData<LoginRegist>()
    val regist: LiveData<LoginRegist>
        get() = _regist

    private val _login = MutableLiveData<LoginRegist>()
    val login: LiveData<LoginRegist>
        get() = _login

    fun createAccount(email: String, passwd: String) {
        // Start create user
        if (TextUtils.isValidEmail(email)) {
            auth.createUserWithEmailAndPassword(email, passwd)
                .addOnSuccessListener {
                    val getRegist = LoginRegist(email, passwd)
                    _regist.value = getRegist
                    addUserData()
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
                    val emailExistsException = task.message
                    if (task.message == emailExistsException)
                        Toast.makeText(context, "The email address is already in use by another account.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
        }
    }

    // Sign-in
    fun signIn(email: String, passwd: String) {
        if (TextUtils.isValidEmail(email)) {
            auth.signInWithEmailAndPassword(email, passwd)
                .addOnSuccessListener {
                    // Check user email
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
                    Log.d(TAG, "$exception")
                    Toast.makeText(context, "The password is not correct", Toast.LENGTH_SHORT).show()
                }
        } else
            Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
    }

    // Sign-out
    fun signOut() {
        Firebase.auth.signOut()
    }

    // Check current user
    fun checkCurrentUser(): FirebaseUser? {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // user is signed in
            return user
        } else {
            // no user is signed in
            return null
        }
    }

    // Add current user data to server
    fun addUserData() {
        val user = hashMapOf(
            "userID" to checkCurrentUser()!!.uid,
            "email" to checkCurrentUser()!!.email,
            "username" to checkCurrentUser()!!.uid,
            "photoUrl" to "default",
            "listConversation" to emptyList<DocumentReference>()
        )
        db.collection("user")
            .document(checkCurrentUser()!!.uid).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: success")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "addUserData: failed")
            }
    }

    companion object {
        private const val TAG = "login_regist"
    }
}