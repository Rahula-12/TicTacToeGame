package com.example.tictactoe.data.model

data class Match(
    val matchId:String="",
    val player1Id:String="",
    val player2Id:String="",
    val winner:String="",
    val firstRow:MutableList<Int> = mutableListOf(-1,-1,-1),
    val secondRow:MutableList<Int> = mutableListOf(-1,-1,-1),
    val thirdRow:MutableList<Int> = mutableListOf(-1,-1,-1),
    val turn:Int=0,
    val direction:Int=-1,
    val playAgain1:Int=-1,
    val playAgain2: Int=-1
)