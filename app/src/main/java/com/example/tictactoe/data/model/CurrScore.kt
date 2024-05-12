package com.example.tictactoe.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrScore(
    @PrimaryKey
    val id:Int=0,
    @ColumnInfo(name="Name1")
    val name1:String="",
    @ColumnInfo(name="Name2")
    val name2:String="",
    @ColumnInfo(name="MatchWon1")
    val matchWon1:Int=0,
    @ColumnInfo(name="MatchWon2")
    val matchWon2: Int=0,
    @ColumnInfo(name="Draw")
    val draw:Int=0
)
