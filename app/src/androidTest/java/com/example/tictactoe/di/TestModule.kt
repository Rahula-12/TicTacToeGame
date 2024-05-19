package com.example.tictactoe.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.tictactoe.data.database.CurrScoreDao
import com.example.tictactoe.data.database.ScoreDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [GameModule::class])
@Module
class TestModule {

    @Provides
    @Singleton
    fun providesScoreDb():ScoreDataBase{
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ScoreDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providesScoreDao(scoreDataBase: ScoreDataBase):CurrScoreDao {
        return scoreDataBase.currScoreDao()
    }

}