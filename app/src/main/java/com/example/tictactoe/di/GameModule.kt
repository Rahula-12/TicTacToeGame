package com.example.tictactoe.di

import android.content.Context
import androidx.room.Room
import com.example.tictactoe.data.database.CurrScoreDao
import com.example.tictactoe.data.database.ScoreDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GameModule {

    @Singleton
    @Provides
    fun providesScoreDb(@ApplicationContext context: Context): ScoreDataBase {
        return Room.databaseBuilder(context, ScoreDataBase::class.java, "score_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesDao(scoreDataBase: ScoreDataBase): CurrScoreDao {
        return scoreDataBase.currScoreDao()
    }

}