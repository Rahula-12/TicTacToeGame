package com.example.tictactoe.data

import android.content.Context

interface GameContainer {
    val repository:GameRepository
}

class CurrScoreContainer(context: Context):GameContainer {
    override val repository: CurrScoreRepository by lazy {
        CurrScoreRepository(ScoreDataBase.getDatabase(context).currScoreDao())
    }
}