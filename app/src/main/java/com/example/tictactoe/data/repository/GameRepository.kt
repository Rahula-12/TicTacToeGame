package com.example.tictactoe.data.repository

import com.example.tictactoe.data.database.CurrScoreDao
import com.example.tictactoe.data.model.CurrScore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GameRepository {
    suspend fun insertCurrentScore(currScore: CurrScore)

    suspend fun updatePlayer1Name(name:String)

    suspend fun updatePlayer2Name(name:String)

    suspend fun updatePlayer1Score(score:Int)

    suspend fun updatePlayer2Score(score:Int)

    suspend fun updateDraw(draw:Int)

    fun currScore():Flow<CurrScore>

    suspend fun prevRecordCount():Int
}

class CurrScoreRepository @Inject constructor(val currScoreDao: CurrScoreDao): GameRepository {
    override suspend fun insertCurrentScore(currScore: CurrScore) = currScoreDao.insertCurrentScore(currScore)

    override suspend fun updatePlayer1Name(name: String) = currScoreDao.updatePlayer1Name(name)

    override suspend fun updatePlayer2Name(name: String) = currScoreDao.updatePlayer2Name(name)

    override suspend fun updatePlayer1Score(score: Int) = currScoreDao.updatePlayer1Score(score)

    override suspend fun updatePlayer2Score(score: Int) = currScoreDao.updatePlayer2Score(score)

    override suspend fun updateDraw(draw: Int) = currScoreDao.updateDraw(draw)
    override fun currScore():Flow<CurrScore> = currScoreDao.currScore()

    override suspend fun prevRecordCount(): Int = currScoreDao.prevRecordCount()

}