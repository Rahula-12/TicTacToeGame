package com.example.tictactoe.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.model.Match
import com.example.tictactoe.data.repository.OnlineGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineGameViewModel @Inject constructor(
        private val onlineGameRepository: OnlineGameRepository
    ):ViewModel() {
        val matchState:StateFlow<Match> = onlineGameRepository.matchState

    fun fetchMatchState(matchId:String) {
        onlineGameRepository.fetchMatchState(matchId)
    }

    fun playAgain(playing:Boolean,matchId: String) {
        onlineGameRepository.playingAgain(playing,matchId)
    }

    fun assignValue(i: Int, j: Int,matchId: String) {
        if (matchState.value.winner == "") {
            val canTouch: Boolean = when (i) {
                0 -> {
                    matchState.value.firstRow[j]==-1
                }
                1 -> {
                    matchState.value.secondRow[j]==-1
                }
                else -> {
                    matchState.value.thirdRow[j]==-1
                }
            }
            if (canTouch) {
                val currTurn = matchState.value.turn
                val firstRow = mutableListOf<Int>()
                firstRow.addAll(matchState.value.firstRow)
                val secondRow= mutableListOf<Int>()
                secondRow.addAll(matchState.value.secondRow)
                val thirdRow= mutableListOf<Int>()
                thirdRow.addAll(matchState.value.thirdRow)
                val gameState: List<MutableList<Int>> = listOf(firstRow,
                    secondRow,
                    thirdRow)
                gameState[i][j] = currTurn
                onlineGameRepository.updateMatch(
                        turn = 1 - currTurn,
                    gameState = gameState,
                    matchId=matchId
                )
                val arr = validateTicTacToe(gameState)
                val direction=arr[0]
                val result=arr[1]
                if (result != -1) {
                    if (result == 1) {
                        onlineGameRepository.updateMatch(winner = matchState.value.player1Id,direction=direction,matchId=matchId)
                    } else if (result == 2) {
                        onlineGameRepository.updateMatch(winner = matchState.value.player2Id,direction=direction,matchId=matchId)
                    } else {
                        onlineGameRepository.updateMatch(winner ="Draw",direction=direction,matchId=matchId)
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

    fun resetMatch(matchId:String) {
        onlineGameRepository.resetMatch(matchId)
    }

    fun removeMatch() {
        onlineGameRepository.removeMatch()
    }

    fun resetUser() {
        onlineGameRepository.resetUser()
    }

}