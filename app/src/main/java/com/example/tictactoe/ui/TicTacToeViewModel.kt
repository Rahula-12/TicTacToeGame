package com.example.tictactoe.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.CurrScore
import com.example.tictactoe.data.CurrScoreDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameState(
    val name1:String="",
    val name2:String="",
    val turn:Int=0,
    val matchesWon1:Int=0,
    val matchesWon2:Int=0,
    val draw:Int=0,
    val visited:List<MutableList<Int>> = listOf(mutableListOf(-1,-1,-1),
                                                mutableListOf(-1,-1,-1),
                                                mutableListOf(-1,-1,-1)),
    val winnerFound:Int=-1,
    val prevRecord:Boolean=false
)

class TicTacToeViewModel(private val currScoreDao: CurrScoreDao):ViewModel() {
    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    init {
        prevRecordExists()
    }

    fun assignValue(i: Int, j: Int) {
        if (gameState.value.winnerFound == -1) {
            if (gameState.value.visited[i][j] == -1) {
                val currTurn = gameState.value.turn
                val visited: List<MutableList<Int>> = gameState.value.visited
                visited[i][j] = currTurn
                _gameState.update {
                    it.copy(
                        turn = 1 - currTurn,
                        visited = visited
                    )
                }
                val result = validateTicTacToe(visited)
                if (result != -1) {
                    if (result == 1) {
                        viewModelScope.launch(Dispatchers.Default) {
                            val matchesWon1 = currScoreDao.matchWonByPlayer1()
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    matchesWon1 = matchesWon1 + 1
                                )
                            }
                            currScoreDao.updatePlayer1Score(matchesWon1 + 1)
                        }
                    } else if (result == 2) {
                        viewModelScope.launch(Dispatchers.Default) {
                            val matchesWon2 = currScoreDao.matchWonByPlayer2()
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    matchesWon2 = matchesWon2 + 1
                                )
                            }
                            currScoreDao.updatePlayer2Score(matchesWon2 + 1)
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.Default) {
                            val draw = currScoreDao.numberOfDraw()
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    draw = draw + 1
                                )
                            }
                            currScoreDao.updateDraw(draw + 1)
                        }
                    }
                }
            }
        }
    }

    private fun validateTicTacToe(visited: List<MutableList<Int>>): Int {
        var allFilled = true
        for (i in 0..2) {
            var count1 = 0
            var notVisited = false
            for (j in 0..2) {
                if (visited[i][j] == -1) {
                    allFilled = false
                    notVisited = true
                    break
                }
                count1 += visited[i][j]
            }
            if (!notVisited) {
                if (count1 == 3) return 2
                else if (count1 == 0) return 1
            }
        }
        for (j in 0..2) {
            var count1 = 0
            var notVisited = false
            for (i in 0..2) {
                if (visited[i][j] == -1) {
                    notVisited = true
                    break
                }
                count1 += visited[i][j]
            }
            if (!notVisited) {
                if (count1 == 3) return 2
                else if (count1 == 0) return 1
            }
        }
        var i = 0
        var j = 0
        var count1 = 0
        var notVisited = false
        while (j < 3) {
            if (visited[i][j] == -1) {
                notVisited = true
                break
            }
            count1 += visited[i][j]
            i++
            j++
        }
        if (!notVisited) {
            if (count1 == 3) return 2
            else if (count1 == 0) return 1
        }
        i = 0
        j = 2
        count1 = 0
        notVisited = false
        while (j >= 0) {
            if (visited[i][j] == -1) {
                notVisited = true
                break
            }
            count1 += visited[i][j]
            i++
            j--
        }
        if (!notVisited) {
            if (count1 == 3) return 2
            else if (count1 == 0) return 1
        }
        if (allFilled) return 0
        return -1
    }

    fun reset(
        temp: Int,
        name1: String = "",
        name2: String = ""
    ) {
        if (temp == 1) {
            _gameState.update {
                it.copy(
                    turn = 0,
                    visited = listOf(
                        mutableListOf(-1, -1, -1),
                        mutableListOf(-1, -1, -1),
                        mutableListOf(-1, -1, -1)
                    ),
                    winnerFound = -1
                )
            }
        } else {
            viewModelScope.launch {
                currScoreDao.updatePlayer1Name(name1)
                currScoreDao.updatePlayer2Name(name2)
                currScoreDao.updatePlayer1Score(0)
                currScoreDao.updatePlayer2Score(0)
                currScoreDao.updateDraw(0)
                _gameState.update {
                    it.copy(
                        name1 = currScoreDao.player1Name(),
                        name2 = currScoreDao.player2Name(),
                        matchesWon1 = 0,
                        matchesWon2 = 0,
                        draw = 0
                    )
                }
            }
        }
    }

    fun insertRecord(name1: String, name2: String) {
        viewModelScope.launch {
            currScoreDao.insertCurrentScore(
                CurrScore(
                    id = 0,
                    name1 = name1,
                    name2 = name2,
                    matchWon1 = 0,
                    matchWon2 = 0,
                    draw = 0
                )
            )
            _gameState.update {
                it.copy(
                    name1 = currScoreDao.player1Name(),
                    name2 = currScoreDao.player2Name(),
                    turn = 0,
                    matchesWon1 = 0,
                    matchesWon2 = 0,
                    draw = 0,
                    prevRecord=true,
                    visited = listOf(
                        mutableListOf(-1, -1, -1),
                        mutableListOf(-1, -1, -1),
                        mutableListOf(-1, -1, -1)
                    ),
                )
            }
        }
    }

    private fun prevRecordExists() {
        viewModelScope.launch(Dispatchers.Main) {
//            Log.d("test",currScoreDao.prevRecordCount().toString())
            val count = currScoreDao.prevRecordCount()
            _gameState.update {
                it.copy(
                    prevRecord = count > 0
                )
            }
//            Log.d("test3", gameState.value.prevRecord.toString())
            if (gameState.value.prevRecord) {
                _gameState.update {
                    it.copy(
                        name1 = currScoreDao.player1Name(),
                        name2 = currScoreDao.player2Name(),
                        matchesWon1 = currScoreDao.matchWonByPlayer1(),
                        matchesWon2 = currScoreDao.matchWonByPlayer2(),
                        draw = currScoreDao.numberOfDraw(),
                        prevRecord = count > 0
                    )
                }
            }
        }
    }
}
class TicTacToeViewModelFactory(private val currScoreDao: CurrScoreDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicTacToeViewModel::class.java)) {
            return TicTacToeViewModel(currScoreDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}