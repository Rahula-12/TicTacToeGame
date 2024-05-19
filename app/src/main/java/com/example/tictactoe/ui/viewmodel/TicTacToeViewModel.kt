package com.example.tictactoe.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.model.CurrScore
import com.example.tictactoe.data.repository.CurrScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameState(
    val name1:String="",
    val name2:String="",
    val turn:Int=0,
    val matchesWon1:Int=0,
    val matchesWon2:Int=0,
    val direction:Int=-1,
    val emptyName:Boolean=false,
    val sameName:Boolean=false,
    val draw:Int=0,
    val visited:List<MutableList<Int>> = listOf(mutableListOf(-1,-1,-1),
                                                mutableListOf(-1,-1,-1),
                                                mutableListOf(-1,-1,-1)),
    val winnerFound:Int=-1,
    val prevRecord:Boolean=true
)

@HiltViewModel
class TicTacToeViewModel @Inject constructor(private val repository: CurrScoreRepository):ViewModel() {
    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState
    private val currScore: Flow<CurrScore> = repository.currScore()
    init {
        viewModelScope.launch {
            val count = repository.prevRecordCount()
            _gameState.update {
                it.copy(
                    prevRecord = count > 0
                )
            }
//            Log.d("test3", gameState.value.prevRecord.toString())
            if (gameState.value.prevRecord) {
                _gameState.update {
                    it.copy(
                        prevRecord = count > 0,
                        direction = -1
                    )
                }
            }
            Log.d("Test",repository.prevRecordCount().toString())
            if (gameState.value.prevRecord) {
                currScore.collect {
                    _gameState.update { temp ->
                        temp.copy(
                            name1 = it.name1,
                            name2 = it.name2,
                            matchesWon1 = it.matchWon1,
                            matchesWon2 = it.matchWon2,
                            draw = it.draw
                        )
                    }
                }
            }
        }
//        Log.d("GameState",_gameState.value.toString())
    }
    fun showAlert(check:Int){
        var currStatus: Boolean
        _gameState.update {
            if(check==0) {
                currStatus=it.emptyName
                it.copy(
                    emptyName = !currStatus
                )
            }
            else {
                currStatus=it.sameName
                it.copy(
                    sameName = !currStatus
                )
            }
        }
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
                val arr = validateTicTacToe(visited)
                val direction=arr[0]
                val result=arr[1]
                if (result != -1) {
                    if (result == 1) {
                        viewModelScope.launch(Dispatchers.Default) {
                            val matchesWon1 = gameState.value.matchesWon1
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    matchesWon1 = matchesWon1 + 1,
                                    direction = direction
                                )
                            }
                            repository.updatePlayer1Score(matchesWon1 + 1)
                        }
                    } else if (result == 2) {
                        viewModelScope.launch(Dispatchers.Default) {
                            val matchesWon2 = gameState.value.matchesWon2
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    matchesWon2 = matchesWon2 + 1,
                                    direction = direction
                                )
                            }
                            repository.updatePlayer2Score(matchesWon2 + 1)
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.Default) {
                            val draw = gameState.value.draw
                            _gameState.update {
                                it.copy(
                                    winnerFound = result,
                                    draw = draw + 1
                                )
                            }
                            repository.updateDraw(draw + 1)
                        }
                    }
                }
            }
        }
    }

    private fun validateTicTacToe(visited: List<MutableList<Int>>): Array<Int> {
        var arr:Array<Int> = arrayOf(-1,-1)
        val allFilled:Array<Boolean> = arrayOf(true)
        arr=validateRowWise(arr, visited,allFilled)
        if(arr[1]!=-1)  return arr
        arr=validateColumnWise(arr, visited)
        if(arr[1]!=-1)  return arr
        arr=validateLeftDiagonal(arr,visited)
        if(arr[1]!=-1)  return arr
        arr=validateRightDiagonal(arr,visited)
        if(arr[1]!=-1)  return arr
        if (allFilled[0]) {
            arr[1]=0
        }
        return arr
    }

    private fun validateRowWise(arr:Array<Int>,visited: List<MutableList<Int>>,allFilled:Array<Boolean>):Array<Int> {
        for (i in 0..2) {
            var count1 = 0
            var notVisited = false
            for (j in 0..2) {
                if (visited[i][j] == -1) {
                    allFilled[0] = false
                    notVisited = true
                    break
                }
                count1 += visited[i][j]
            }
            if (!notVisited) {
                if (count1 == 3) {
                    arr[0]=i+1
                    arr[1]=2
                    return arr
                }
                else if (count1 == 0) {
                    arr[0]=i+1
                    arr[1]=1
                    return arr
                }
            }
        }
        return arr
    }

    private fun validateColumnWise(arr: Array<Int>, visited: List<MutableList<Int>>):Array<Int> {
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
                if (count1 == 3) {
                    arr[0]=j+4
                    arr[1]=2
                    return arr
                }
                else if (count1 == 0) {
                    arr[0]=j+4
                    arr[1]=1
                    return arr
                }
            }
        }
        return arr
    }

    private fun validateLeftDiagonal(arr: Array<Int>, visited: List<MutableList<Int>>):Array<Int> {
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
            if (count1 == 3) {
                arr[0]=7
                arr[1]=2
                return arr
            }
            else if (count1 == 0) {
                arr[0]=7
                arr[1]=1
                return arr
            }
        }
        return arr
    }

    private fun validateRightDiagonal(arr: Array<Int>, visited: List<MutableList<Int>>):Array<Int> {
        var i = 0
        var j = 2
        var count1 = 0
        var notVisited = false
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
            if (count1 == 3) {
                arr[0]=8
                arr[1]=2
                return arr
            }
            else if (count1 == 0) {
                arr[0]=8
                arr[1]=1
                return arr
            }
        }
        return arr
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
                    direction = -1,
                    winnerFound = -1
                )
            }
        } else {
            viewModelScope.launch {
                repository.updatePlayer1Name(name1)
                repository.updatePlayer2Name(name2)
                repository.updatePlayer1Score(0)
                repository.updatePlayer2Score(0)
                repository.updateDraw(0)
            }
        }
    }

    fun insertRecord(name1: String, name2: String) {
        viewModelScope.launch {
            repository.insertCurrentScore(
                CurrScore(
                    id = 0,
                    name1 = name1,
                    name2 = name2,
                    matchWon1 = 0,
                    matchWon2 = 0,
                    draw = 0
                )
            )
        }
        _gameState.update {
            it.copy(
                name1=name1,
                name2=name2,
                turn = 0,
                direction = -1,
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