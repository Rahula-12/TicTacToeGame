package com.example.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrScore::class],version = 6, exportSchema = false)
abstract class ScoreDataBase: RoomDatabase() {
    abstract fun currScoreDao(): CurrScoreDao
}