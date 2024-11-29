package com.example.tictactoe.data.repository

import android.util.Log
import com.example.tictactoe.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PlayersRepository @Inject constructor() {
    private val usersRef: CollectionReference = FirebaseFirestore.getInstance().collection("users")
    private val _userList:MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    private val _invited:MutableStateFlow<String> = MutableStateFlow("")
    val invited:StateFlow<String> = _invited
    val userList: StateFlow<List<User>> = _userList.asStateFlow()
    init {
        usersRef.addSnapshotListener{snapshot, exception->
            if(exception!=null) return@addSnapshotListener
            val list: MutableList<User> = mutableListOf()
            for (document in snapshot!!.documents) {
                if(document["emailId"]==FirebaseAuth.getInstance().currentUser?.email) {
                        _invited.value=document["invitedBy"].toString()
                }
//                println(document.get("emailId").toString()+" "+currentUserEmail+" "+(document.get("emailId").toString()==currentUserEmail))
                val ans=document.get("playing")==false
                if(ans)
                 list.add(document.toObject(User::class.java)!!)
            }
            _userList.value = list
        }
        getUsers()
    }

    fun registerUser() {
        val firebaseAuth=FirebaseAuth.getInstance()
        val user= User(
            emailId=firebaseAuth.currentUser?.email?:"",
            matchesLost=0,
            matchesWon = 0,
            matchesDraw = 0,
            isPlaying = false,
            totalMatches= mutableMapOf()
        )
        usersRef.document(FirebaseAuth.getInstance().currentUser!!.email!!).set(user)
    }

    private fun getUsers() {
        val coroutineScope= CoroutineScope(Dispatchers.IO+ SupervisorJob())
        coroutineScope.launch {
            val task=usersRef.get().await()
            val list:MutableList<User> = mutableListOf()
            for(document in task.documents) {
//                println(document.get("emailId").toString()+" "+currentUserEmail+" "+(document.get("emailId").toString()==currentUserEmail))
                val ans=document.get("playing")==false
                if(ans)
                    list.add(document.toObject(User::class.java)!!)
            }
            _userList.value=list
            Log.d("usersInRepo",_userList.value.toString())
        }
    }

    fun declineInvite() {
        usersRef.document(invited.value).update("invitedBy","Declined Invite")
        usersRef.document(invited.value).update("invitedBy","")
        val task=usersRef.document(FirebaseAuth.getInstance().currentUser!!.email!!).update("invitedBy","")
        val coroutineScope= CoroutineScope(Dispatchers.IO+ SupervisorJob())
        coroutineScope.launch {
            if (task.isSuccessful) {
                Log.d("Declined","Success")
            }
            else {
                Log.d("Declined",task.exception?.message.toString())
            }
        }
    }

    fun acceptInvite(senderEmail:String) {
        usersRef.document(invited.value).update("invitedBy","Accepted Invite")
        usersRef.document(invited.value).update("invitedBy","")
        usersRef.document(FirebaseAuth.getInstance().currentUser!!.email!!).update("invitedBy","")
        usersRef.document(FirebaseAuth.getInstance().currentUser!!.email!!).update("playing",true)
        usersRef.document(senderEmail).update("playing",true)
    }

    fun sendInvite(selectedPlayer:String) {
        usersRef.document(selectedPlayer).update("invitedBy",FirebaseAuth.getInstance().currentUser!!.email)
    }

}