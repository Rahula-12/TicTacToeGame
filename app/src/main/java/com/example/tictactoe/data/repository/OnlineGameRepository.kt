package com.example.tictactoe.data.repository

import com.example.tictactoe.data.model.Match
import com.example.tictactoe.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class OnlineGameRepository @Inject constructor() {

    private val _matchState: MutableStateFlow<Match> = MutableStateFlow(Match())
    val matchState: StateFlow<Match> = _matchState
    private val matchesReference = FirebaseFirestore.getInstance().collection("matches")
    private val usersReference = FirebaseFirestore.getInstance().collection("users")

    private fun initializeMails(senderMail: String, invitedMail: String) {
        val key = senderMail + "_" + invitedMail
        val match = Match(matchId = key, player1Id = senderMail, player2Id = invitedMail)
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
            val task = matchesReference.document(key).get().await()
            if (task?.toObject(Match::class.java) == null) {
                matchesReference.document(key).set(match)
            }
        }
    }

    fun fetchMatchState(matchId: String) {
        val senderMail = matchId.split("_")[0]
        val invitedMail = matchId.split("_")[1]
        initializeMails(senderMail, invitedMail)
        matchesReference.document(matchId).addSnapshotListener { documentSnapshot, exception ->
            if (exception != null) return@addSnapshotListener
            documentSnapshot?.toObject(Match::class.java)?.let { currMatch ->
                if (currMatch != _matchState.value) {
                    _matchState.value = currMatch
                }
            }
        }
    }

    fun updateMatch(
        winner: String? = null,
        gameState: List<MutableList<Int>>? = null,
        turn: Int? = null,
        direction: Int? = null,
        matchId: String
    ) {
        val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val senderMail = matchId.split("_")[0]
        val invitedMail = matchId.split("_")[1]
        coroutineScope.launch {
            winner?.let { it ->
                matchesReference.document(matchId).update("winner", it)
                if (it != "Draw") {
                    usersReference.document(it).get().addOnSuccessListener { doc ->
                        val matchesWon = doc.get("matchesWon") as? Long
                        val matchesWonInt = matchesWon?.toInt() ?: 0
                        usersReference.document(it).update("matchesWon", 1 + matchesWonInt)
                        val scope=CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            val winnerUser=usersReference.document(it).get().await().toObject(User::class.java)
                            winnerUser?.let { curr->
                                val totalMatches=curr.totalMatches
                                if(it==senderMail) {
                                    if(!totalMatches.containsKey(invitedMail)) {
                                        totalMatches[invitedMail] = 1
                                    }
                                    else {
                                        totalMatches[invitedMail] = totalMatches[invitedMail]!!+1
                                    }
                                }
                                else {
                                    if(!totalMatches.containsKey(senderMail)) {
                                        totalMatches[senderMail] = 1
                                    }
                                    else {
                                        totalMatches[senderMail] = totalMatches[senderMail]!!+1
                                    }
                                }
                            }
                            usersReference.document(it).set(winnerUser!!)
                        }
                    }
                    if (it == senderMail) {
                        usersReference.document(invitedMail).get().addOnSuccessListener { doc ->
                            val matchesLost = doc.get("matchesLost") as? Long
                            val matchesLostInt = matchesLost?.toInt() ?: 0
                            usersReference.document(invitedMail).update("matchesLost", 1 + matchesLostInt)
                            usersReference.document(invitedMail).get().addOnSuccessListener {curr->
                                val user=curr.toObject(User::class.java)
                                user?.let { u->
                                    val totalMatches=u.totalMatches
                                    if(totalMatches[senderMail]!=null) {
                                        totalMatches[senderMail]=  totalMatches[senderMail]!!+1
                                    }
                                    else {
                                        totalMatches[senderMail]=1
                                    }
                                }
                                usersReference.document(invitedMail).set(user!!)
                            }
                        }
                    } else {
                        usersReference.document(senderMail).get().addOnSuccessListener { doc ->
                            val matchesLost = doc.get("matchesLost") as? Long
                            val matchesLostInt = matchesLost?.toInt() ?: 0
                            usersReference.document(senderMail).update("matchesLost", 1 + matchesLostInt)
                            usersReference.document(senderMail).get().addOnSuccessListener {curr->
                                val user=curr.toObject(User::class.java)
                                user?.let { u->
                                    val totalMatches=u.totalMatches
                                    if(totalMatches[invitedMail]!=null) {
                                        totalMatches[invitedMail]=  totalMatches[invitedMail]!!+1
                                    }
                                    else {
                                        totalMatches[invitedMail]=1
                                    }
                                }
                                usersReference.document(senderMail).set(user!!)
                            }
                        }
                    }
                } else {

                    usersReference.document(senderMail).get().addOnSuccessListener { doc ->
                        val matchesDraw = doc.get("matchesDraw") as? Long
                        val matchesDrawInt = matchesDraw?.toInt() ?: 0
                        usersReference.document(senderMail).update("matchesDraw", 1 + matchesDrawInt)
                        val user=doc.toObject(User::class.java)
                        user?.let { curr->
                            val totalMatches=curr.totalMatches
                            if(totalMatches[invitedMail]!=null) {
                                totalMatches[invitedMail]=totalMatches[invitedMail]!!+1
                            }
                            else {
                                totalMatches[invitedMail]=1
                            }
                        }
                        usersReference.document(senderMail).set(user!!)
                    }
                    usersReference.document(invitedMail).get().addOnSuccessListener { doc ->
                        val matchesDraw = doc.get("matchesDraw") as? Long
                        val matchesDrawInt = matchesDraw?.toInt() ?: 0
                        usersReference.document(invitedMail).update("matchesDraw", 1 + matchesDrawInt)
                        val user=doc.toObject(User::class.java)
                        user?.let { curr->
                            val totalMatches=curr.totalMatches
                            if(totalMatches[senderMail]!=null) {
                                totalMatches[senderMail]=totalMatches[senderMail]!!+1
                            }
                            else {
                                totalMatches[senderMail]=1
                            }
                        }
                        usersReference.document(invitedMail).set(user!!)
                    }
                }
            }
            gameState?.let { it ->
                matchesReference.document(matchId).update("firstRow", it[0])
                matchesReference.document(matchId).update("secondRow", it[1])
                matchesReference.document(matchId).update("thirdRow", it[2])
            }
            turn?.let { it ->
                matchesReference.document(matchId).update("turn", it)
            }
            direction?.let { it ->
                matchesReference.document(matchId).update("direction", it)
            }
        }
    }

    fun removeMatch(matchId:String) {
        val coroutineScope= CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val match = Match(matchId = matchId, player1Id = matchId.split("_")[0], player2Id = matchId.split("_")[1])
            val task=matchesReference.document(matchId).set(match)
        }
    }
}