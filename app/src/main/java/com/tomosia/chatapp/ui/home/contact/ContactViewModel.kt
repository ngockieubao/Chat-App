package com.tomosia.chatapp.ui.home.contact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.tomosia.chatapp.model.User

class ContactViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth.currentUser

    private val _users = MutableLiveData<List<User?>>()
    val users: LiveData<List<User?>>
        get() = _users
    private val listUserToObject = mutableListOf<User>()

    private val _friends = MutableLiveData<List<User?>>()
    val friends: LiveData<List<User?>>
        get() = _friends
    private var listFriendShow = mutableListOf<User>()
    private var listFriendRemove = mutableListOf<User>()

    private val userRef = db.collection("user")

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

    fun readListAddFriend() {
        userRef.get()
            .addOnSuccessListener { result ->
                listUserToObject.clear()
                // Get list user
                for (document in result) {
                    val userToObject = document.toObject<User>()
                    // Exclude current user from list user
                    if (document.id != checkCurrentUser()?.uid) {
                        listUserToObject.add(userToObject)
                    }
                }
                listFriendRemove = listUserToObject
//                // Remove list friend from list user
//                _friends.value?.let {
//                    listFriendRemove.removeAll(it)
//                }
//                _users.value = listFriendRemove

                // get list friend
                checkCurrentUser()?.uid?.let {
                    userRef.document(it).get()
                        .addOnSuccessListener { result ->
                            val userToObject = result.toObject<User>()
                            val listFriendCurUser = userToObject?.listFriend
                            if (listFriendCurUser != null) {
                                Log.d(TAG, "readListAddFriend = $listFriendCurUser")
                                val listFriendSize = listFriendCurUser.size
                                for (i in 0..listFriendSize.minus(1)) {
                                    userRef.document(listFriendCurUser[i]).get()
                                        .addOnSuccessListener { resultUser ->
                                            val userDataToObject = resultUser.toObject<User>()
                                            if (userDataToObject != null) {
                                                listFriendShow.add(userDataToObject)
//                                                if (i == listFriendSize.minus(1)) {
//                                                    _users.value?.let {
//                                                        listFriendRemove.removeAll(listFriendShow)
//                                                        Log.d(TAG, "readListAddFriend - get list friend success: $listFriendShow")
//                                                    }
////                                                    _users.value = listFriendRemove.removeAll(listFriendShow)
//                                                }
//                                                Log.d(TAG, "readListAddFriend - get list friend failed")
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.d(TAG, "get user in list friend fail: ${exception.message}")
                                        }
                                }
                                _users.value?.let {
                                    listFriendRemove.removeAll(listFriendShow)
                                    Log.d(TAG, "readListAddFriend - get list friend success: $listFriendShow")
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "readListFriend fail: ${exception.message}")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "readUser: Error getting documents", exception)
            }
    }

    fun readListFriend() {
        checkCurrentUser()?.uid?.let {
            userRef.document(it).get()
                .addOnSuccessListener { result ->
                    val userToObject = result.toObject<User>()
                    val listFriendToObject = userToObject!!.listFriend
                    listFriendShow.clear()
                    if (listFriendToObject != null) {
                        showListFriend(listFriendToObject)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "readListFriend fail: ${exception.message}")
                }
        }
    }

    private fun showListFriend(listFriendToObject: List<String>) {
        val listFriendSize = listFriendToObject.size
        for (i in 0..listFriendSize.minus(1)) {
            userRef.document(listFriendToObject[i]).get()
                .addOnSuccessListener { resultUser ->
                    val userDataToObject = resultUser.toObject<User>()
                    if (userDataToObject != null) {
                        listFriendShow.add(userDataToObject)
                        if (i == listFriendSize.minus(1)) {
                            _friends.value = listFriendShow
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get user in list friend fail: ${exception.message}")
                }
        }
    }

    // add friend to lisFriend
    fun addFriend(user: User?) {
        val curUserID = checkCurrentUser()?.uid
        val friendID = user?.userID
        // update listFriend of current user
        if (curUserID != null && friendID != null) {
            userRef.document(curUserID).update("listFriend", FieldValue.arrayUnion(user.userID))
            userRef.document(friendID).update("listFriend", FieldValue.arrayUnion(curUserID))
        }
    }

    // Sign-out
    fun signOut() {
        Firebase.auth.signOut()
        _users.value = null
        _friends.value = null
    }

    companion object {
        private const val TAG = "contactviewmodel"
    }
}