package com.example.calmness.viewmodel

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import com.example.calmness.model.User

class ViewModel : ViewModel() {

    private val userRef =
        Firebase.database("https://calmness-d0600-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("USER")
    private val dbRef =
        Firebase.database("https://calmness-d0600-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference()
    var storageRef = FirebaseStorage.getInstance().getReference("AvatarBD")
    private var auth: FirebaseAuth = Firebase.auth
    var localUri: Uri? = null
    var localUser: MutableLiveData<User> = MutableLiveData()
    var mood: MutableLiveData<String> = MutableLiveData()

    fun takeUser(): LiveData<User> {
        return localUser
    }

    fun getMood(): LiveData<String> {
        return mood
    }

    fun happyMood() {
        dbRef.child("Happy").get().addOnSuccessListener {
            mood.value = it.value.toString()
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun normalMood() {
        dbRef.child("Hormal").get().addOnSuccessListener {
            mood.value = it.value.toString()
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun badMood() {
        val rnds = (1..2).random()
        if (rnds == 1) {
            dbRef.child("Bad").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        } else {
            dbRef.child("Bad2").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }

    fun irritableMood() {
        val rnds = (1..2).random()
        if (rnds == 1) {
            dbRef.child("Irritable").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        } else {
            dbRef.child("Irritable2").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }

    fun getUser() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val user: User = it.getValue(User::class.java)!!
                    if (user.id == auth.currentUser!!.uid) {
                        localUser.value = user
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        userRef.addValueEventListener(postListener)
    }

    fun pushUser(
        name: String, email: String, age: String, weight: String,
        arteriotony: String, phone: String
    ) {
        var photoUrl = ""
        if (localUri != null) {
            photoUrl = localUri.toString()
        }
        val user =
            User(auth.currentUser!!.uid, photoUrl, name, email, age, weight, arteriotony, phone)
        userRef.child(auth.currentUser!!.uid).setValue(user)
    }

    fun saveAvatar(byteArray: ByteArray) {

        val localRef = storageRef.child(auth.currentUser!!.email.toString() + "Avatar")
        val uploadTask = localRef.putBytes(byteArray)

        uploadTask
            .addOnCompleteListener {
                localUri = it.result.uploadSessionUri
            }
        localRef.downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    localUri = task.result
                }
            }
    }

    fun exit() {
        auth.signOut()
    }
}