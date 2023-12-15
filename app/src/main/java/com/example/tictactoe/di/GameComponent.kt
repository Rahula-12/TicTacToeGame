package com.example.tictactoe.di

import android.content.Context
import com.example.tictactoe.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ScoreDbModule::class])
interface GameComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance context: Context)

    }

}