package com.example.tictactoe.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrScoreDao {
    @Insert
    suspend fun insertCurrentScore(currScore: CurrScore)

    @Query("Update CurrScore set name1=:name")
    suspend fun updatePlayer1Name(name:String)

    @Query("Update CurrScore set name2=:name")
    suspend fun updatePlayer2Name(name:String)

    @Query("Update CurrScore set matchWon1=:score")
    suspend fun updatePlayer1Score(score:Int)

    @Query("Update CurrScore set matchWon2=:score")
    suspend fun updatePlayer2Score(score:Int)

    @Query("Update CurrScore set draw=:draw")
    suspend fun updateDraw(draw:Int)

    @Query("Select matchWon1 from CurrScore")
    suspend fun matchWonByPlayer1():Int

    @Query("Select matchWon2 from CurrScore")
    suspend fun matchWonByPlayer2():Int

    @Query("Select draw from CurrScore")
    suspend fun numberOfDraw():Int

    @Query("Select name1 from CurrScore")
    suspend fun player1Name():String

    @Query("Select name2 from CurrScore")
    suspend fun player2Name():String

    @Query("Select count(*) from CurrScore")
    suspend fun prevRecordCount():Int
}