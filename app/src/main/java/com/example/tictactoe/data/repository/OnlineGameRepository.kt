package com.example.tictactoe.data.repository

import com.example.tictactoe.data.model.Match
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineGameRepository @Inject constructor() {

    private lateinit var senderMail:String
    private lateinit var invitedMail:String
    private val _matchState:MutableStateFlow<Match> = MutableStateFlow(Match())
    val matchState: StateFlow<Match> = _matchState
    private val matchesReference=FirebaseFirestore.getInstance().collection("matches")
    private val usersReference=FirebaseFirestore.getInstance().collection("users")

    private fun initializeMails() {
        val key=senderMail+"_"+invitedMail
        val match=Match(matchId = key, player1Id = senderMail, player2Id = invitedMail)
        val coroutineScope= CoroutineScope(Dispatchers.Default+ SupervisorJob())
        coroutineScope.launch {
            val task=matchesReference.document(key).get()
            if(task.exception!=null)
            matchesReference.document(key).set(match)
        }
    }

    fun fetchMatchState(matchId:String) {
        if(!::senderMail.isInitialized) {
            senderMail=matchId.split("_")[0]
            invitedMail=matchId.split("_")[1]
            initializeMails()
        val coroutineScope= CoroutineScope(Dispatchers.Default+ SupervisorJob())
        coroutineScope.launch {
            matchesReference.document(matchId).addSnapshotListener{documentSnapShot,exception->
                if(exception!=null) return@addSnapshotListener
                val currMatch=documentSnapShot?.toObject(Match::class.java)
                currMatch?.let {curr->
                    _matchState.update {
                        it.copy(
                            matchId = curr.matchId,
                            player1Id=curr.player1Id,
                            player2Id=curr.player2Id,
                            winner=curr.winner,
                            firstRow = curr.firstRow,
                            secondRow = curr.secondRow,
                            thirdRow=curr.thirdRow,
                            turn=curr.turn,
                            direction=curr.direction
                        )
                    }
                }
            }
        }
    }
        }



    fun updateMatch(winner:String?=null,gameState:List<MutableList<Int>>?=null,turn:Int?=null,direction:Int?=null) {
        val coroutineScope= CoroutineScope(Dispatchers.Default+ SupervisorJob())
        coroutineScope.launch {
            launch {
                winner?.let {it->
                    matchesReference.document(matchState.value.matchId).update("winner", it)
                    if(it!="Draw") {
                        usersReference.document(it).get().addOnSuccessListener { doc->
                            val matchesWon = doc.get("matchesWon") as? Long
                            val matchesWonInt = matchesWon?.toInt() ?: 0
                            usersReference.document(it).update("matchesWon", 1 + matchesWonInt)
                        }
                        if(it==senderMail) {
                            usersReference.document(invitedMail).get().addOnSuccessListener { doc->
//                                val matchesLost=doc.get("matchesLost") as Int
//                                usersReference.document(invitedMail).update("matchesLost",1+matchesLost)
                                val matchesLost = doc.get("matchesLost") as? Long
                                val matchesLostInt = matchesLost?.toInt() ?: 0
                                usersReference.document(invitedMail).update("matchesLost", 1 + matchesLostInt)
                            }
                        }
                        else {
                            usersReference.document(senderMail).get().addOnSuccessListener { doc->
                                val matchesLost = doc.get("matchesLost") as? Long
                                val matchesLostInt = matchesLost?.toInt() ?: 0
                                usersReference.document(senderMail).update("matchesLost", 1 + matchesLostInt)
                            }
                        }
                    }
                    else {
                        usersReference.document(senderMail).get().addOnSuccessListener { doc->
//                            val matchesDraw=doc.get("matchesDraw") as Int
//                            usersReference.document(senderMail).update("matchesDraw",1+matchesDraw)
                            val matchesDraw = doc.get("matchesDraw") as? Long
                            val matchesDrawInt = matchesDraw?.toInt() ?: 0
                            usersReference.document(senderMail).update("matchesDraw", 1 + matchesDrawInt)
                        }
                        usersReference.document(invitedMail).get().addOnSuccessListener { doc->
                            val matchesDraw = doc.get("matchesDraw") as? Long
                            val matchesDrawInt = matchesDraw?.toInt() ?: 0
                            usersReference.document(invitedMail).update("matchesDraw", 1 + matchesDrawInt)
                        }
                    }
                }
            }
            launch {
                gameState?.let {it->
                    matchesReference.document(matchState.value.matchId).update("firstRow",it[0])
                    matchesReference.document(matchState.value.matchId).update("secondRow",it[1])
                    matchesReference.document(matchState.value.matchId).update("thirdRow",it[2])
                }
            }
            launch {
                turn?.let {it->
                    matchesReference.document(matchState.value.matchId).update("turn",it)
                }
            }
            launch {
                direction?.let {it->
                    matchesReference.document(matchState.value.matchId).update("direction",it)
                }
            }
        }
    }
}