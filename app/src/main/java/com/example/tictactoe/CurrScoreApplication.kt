package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.di.DaggerGameComponent
import com.example.tictactoe.di.GameComponent

class CurrScoreApplication: Application() {
    lateinit var component: GameComponent
    override fun onCreate() {
        super.onCreate()
        component=DaggerGameComponent.factory().create(this)
    }
}