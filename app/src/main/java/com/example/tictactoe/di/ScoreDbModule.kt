package com.example.tictactoe.di

import android.content.Context
import androidx.room.Room
import com.example.tictactoe.data.CurrScoreDao
import com.example.tictactoe.data.ScoreDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ScoreDbModule {

    @Singleton
    @Provides
    fun providesScoreDb(context: Context):ScoreDataBase {
        return Room.databaseBuilder(context, ScoreDataBase::class.java, "score_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesDao(scoreDataBase: ScoreDataBase):CurrScoreDao{
        return scoreDataBase.currScoreDao()
    }

}