package com.example.tictactoe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tictactoe.data.database.CurrScoreDao
import com.example.tictactoe.data.model.CurrScore

@Database(entities = [CurrScore::class],version = 6, exportSchema = false)
abstract class ScoreDataBase: RoomDatabase() {
    abstract fun currScoreDao(): CurrScoreDao
}