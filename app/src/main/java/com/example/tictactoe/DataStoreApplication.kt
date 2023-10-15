package com.example.tictactoe

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tictactoe.data.DataStoreRepository

private val SCORE_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SCORE_PREFERENCE_NAME
)
class DataStoreApplication:Application() {
    lateinit var dataStoreRepository: DataStoreRepository
    override fun onCreate() {
        super.onCreate()
        dataStoreRepository=DataStoreRepository(dataStore)
    }
}