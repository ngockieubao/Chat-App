package com.tomosia.chatapp.ui.home.contact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.User

class ContactViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val listUserToObject = mutableListOf<User>()

    private val _friends = MutableLiveData<List<User>>()
    val friends: LiveData<List<User>>
        get() = _friends

    private val listFriendShow = mutableListOf<User>()

    private fun checkCurrentUser(): FirebaseUser? {
        val user = auth
        if (user != null) {
            // user is signed in
            return user
        } else {
            // no user is signed in
            return null
        }
    }

    private val userRef = db.collection("user")
    fun readListUser() {
        userRef.get()
            .addOnSuccessListener { result ->
                listUserToObject.clear()
                for (document in result) {
                    val userToObject = document.toObject<User>()
                    // Exclude current user from list user
                    if (document.id != checkCurrentUser()!!.uid) {
                        listUserToObject.add(userToObject)
                    }
                }
                // Remove list friend from list user
                _friends.value?.let { listUserToObject.removeAll(it) }
                _users.value = listUserToObject
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "readUser: Error getting documents", exception)
            }
    }

    fun readListFriend() {
        userRef.document(checkCurrentUser()!!.uid).get()
            .addOnSuccessListener { result ->
                val userToObject = result.toObject<User>()
                val listFriendToObject = userToObject!!.listFriend
                listFriendShow.clear()
                showListFriend(listFriendToObject)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "readListFriend fail: ${exception.message}")
            }
    }

    private fun showListFriend(listFriendToObject: List<String>) {
        for (i in 0..listFriendToObject.size.minus(1)) {
            userRef.document(listFriendToObject[i]).get()
                .addOnSuccessListener { resultUser ->
                    val userDataToObject = resultUser.toObject<User>()
                    if (userDataToObject != null) {
                        listFriendShow.add(userDataToObject)
                        if (i == listFriendToObject.size.minus(1)) {
                            _friends.value = listFriendShow
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get user in list friend fail: ${exception.message}")
                }
        }
    }

    fun readCurrentUserConversation() {
        userRef.document(checkCurrentUser()!!.uid).get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "readUserData: ${result.data} <= ${result.toObject<com.tomosia.chatapp.model.User>()}")
                val listResult = result.toObject<com.tomosia.chatapp.model.User>()
                if (listResult != null) {
                    Log.d(TAG, "readUserData: ${listResult.listConversation[0].id}")
                    try {
                        val listCon = listResult.listConversation[0].id
                        db.collection("conversation").document(listCon).get()
                            .addOnSuccessListener { result ->
                                Log.d(TAG, "readConversation: ${result.data}")
                            }
                            .addOnFailureListener { exception ->
                                Log.d(TAG, "readConversation: ${exception.message}")
                            }
                    } catch (ex: IndexOutOfBoundsException) {
                        Log.d(TAG, "readUserData: ${ex.message}")
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "readUserData: read data failed")
            }
    }

    companion object {
        private const val TAG = "contactviewmodel"
    }
}