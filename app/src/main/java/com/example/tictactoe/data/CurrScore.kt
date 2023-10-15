package com.example.tictactoe.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
data class CurrScore(
    val id:Int=0,
    val name1:String="",
    val name2:String="",
    val matchWon1:Int=0,
    val matchWon2: Int=0,
    val draw:Int=0
)
