package com.example.tictactoe.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

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

    @Query("Select * from CurrScore limit 1")
    fun currScore(): Flow<CurrScore>

    @Query("Select count(*) from CurrScore")
    suspend fun prevRecordCount():Int
}