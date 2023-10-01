package com.example.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrScore::class],version = 6, exportSchema = false)
abstract class ScoreDataBase: RoomDatabase() {
    abstract fun currScoreDao(): CurrScoreDao
    companion object{
        @Volatile
        private var INSTANCE:ScoreDataBase?=null
        fun getDatabase(context: Context): ScoreDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, ScoreDataBase::class.java, "score_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}