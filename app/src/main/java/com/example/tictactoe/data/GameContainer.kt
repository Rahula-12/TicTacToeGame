package com.example.tictactoe.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

interface GameContainer {
    val repository:GameRepository
}
class CurrScoreContainer(context: Context):GameContainer {
    override val repository: CurrScoreRepository by lazy {
        CurrScoreRepository(ScoreDataBase.getDatabase(context).currScoreDao())
    }
}