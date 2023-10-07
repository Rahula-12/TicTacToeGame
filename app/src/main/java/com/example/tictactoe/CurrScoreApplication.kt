package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.data.CurrScoreContainer
import com.example.tictactoe.data.GameContainer

class CurrScoreApplication: Application() {
    lateinit var container: GameContainer
    override fun onCreate() {
        super.onCreate()
        container=CurrScoreContainer(this)
    }
}