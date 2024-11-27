package com.example.tictactoe.data.model

data class User(
    var emailId:String="",
    var matchesLost:Int=0,
    var matchesWon:Int=0,
    var matchesDraw:Int=0,
    var isPlaying:Boolean=false,
    var totalMatches:MutableMap<String,Int> = mutableMapOf(),
    var invitedBy:String=""
)
