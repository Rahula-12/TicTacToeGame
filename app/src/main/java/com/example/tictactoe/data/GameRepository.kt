package com.example.tictactoe.data

import android.content.Context
import androidx.room.Insert
import androidx.room.Query

interface GameRepository {
    suspend fun insertCurrentScore(currScore: CurrScore)

    suspend fun updatePlayer1Name(name:String)

    suspend fun updatePlayer2Name(name:String)

    suspend fun updatePlayer1Score(score:Int)

    suspend fun updatePlayer2Score(score:Int)

    suspend fun updateDraw(draw:Int)

    suspend fun matchWonByPlayer1():Int

    suspend fun matchWonByPlayer2():Int

    suspend fun numberOfDraw():Int

    suspend fun player1Name():String

    suspend fun player2Name():String

    suspend fun prevRecordCount():Int
}

class CurrScoreRepository(val currScoreDao: CurrScoreDao):GameRepository {
    override suspend fun insertCurrentScore(currScore: CurrScore) = currScoreDao.insertCurrentScore(currScore)

    override suspend fun updatePlayer1Name(name: String) = currScoreDao.updatePlayer1Name(name)

    override suspend fun updatePlayer2Name(name: String) = currScoreDao.updatePlayer2Name(name)

    override suspend fun updatePlayer1Score(score: Int) = currScoreDao.updatePlayer1Score(score)

    override suspend fun updatePlayer2Score(score: Int) = currScoreDao.updatePlayer2Score(score)

    override suspend fun updateDraw(draw: Int) = currScoreDao.updateDraw(draw)

    override suspend fun matchWonByPlayer1(): Int = currScoreDao.matchWonByPlayer1()

    override suspend fun matchWonByPlayer2(): Int = currScoreDao.matchWonByPlayer2()

    override suspend fun numberOfDraw(): Int = currScoreDao.numberOfDraw()

    override suspend fun player1Name(): String = currScoreDao.player1Name()

    override suspend fun player2Name(): String = currScoreDao.player2Name()

    override suspend fun prevRecordCount(): Int = currScoreDao.prevRecordCount()

}