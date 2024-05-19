package com.example.tictactoe

import com.example.tictactoe.data.database.CurrScoreDao
import com.example.tictactoe.data.model.CurrScore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HiltDependencyInjectionTest {

    @get:Rule
    val hiltAndroidRule=HiltAndroidRule(this)

    @Inject
    lateinit var currScoreDao: CurrScoreDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        hiltAndroidRule.inject()
        val score=CurrScore(
            name1="Rahul",
            name2 = "Diya",
            matchWon1=1,
            matchWon2 = 2,
            draw = 3
        )
        runTest {
            currScoreDao.insertCurrentScore(score)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testing_prev_record_count() = runTest{
        val count=currScoreDao.prevRecordCount()
        Assert.assertEquals(1,count)
    }

    @Test
    fun test_update_draw()= runTest{
        currScoreDao.updateDraw(5)
        val newScore=currScoreDao.currScore().first()
        Assert.assertEquals(5,newScore.draw)
    }

    @Test
    fun test_update_player2_score()= runTest {
        currScoreDao.updatePlayer2Score(8)
        val newScore=currScoreDao.currScore().first()
        Assert.assertEquals(8,newScore.matchWon2)
    }

    @Test
    fun test_update_player1_score()= runTest {
        currScoreDao.updatePlayer1Score(3)
        val newScore=currScoreDao.currScore().first()
        Assert.assertEquals(3,newScore.matchWon1)
    }

    @Test
    fun test_update_player1_name()= runTest {
        currScoreDao.updatePlayer1Name("Tony Stark")
        val newScore=currScoreDao.currScore().first()
        Assert.assertEquals("Tony Stark",newScore.name1)
    }

    @Test
    fun test_update_player2_name()= runTest {
        currScoreDao.updatePlayer2Name("Pepper Potts")
        val newScore=currScoreDao.currScore().first()
        Assert.assertEquals("Pepper Potts",newScore.name2)
    }

}