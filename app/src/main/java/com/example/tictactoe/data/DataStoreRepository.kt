package com.example.tictactoe.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository(
    private val dataStore:DataStore<Preferences>
) {
    private companion object {
        val name1= stringPreferencesKey("name1")
        val name2= stringPreferencesKey("name2")
        val matchesWon1= intPreferencesKey("matches_won_1")
        val matchesWon2= intPreferencesKey("matches_won_2")
        val draw= intPreferencesKey("draw")
    }

    suspend fun saveName1(name:String) {
        dataStore.edit {
            it[name1]=name
        }
    }

    suspend fun saveName2(name:String) {
        dataStore.edit {
            it[name2]=name
        }
    }

    suspend fun saveMatchWon1(matches:Int) {
        dataStore.edit {
            it[matchesWon1]=matches
        }
    }

    suspend fun saveMatchWon2(matches:Int) {
        dataStore.edit {
            it[matchesWon2]=matches
        }
    }

    suspend fun saveDraw(matches:Int) {
        dataStore.edit {
            it[draw]=matches
        }
    }

    suspend fun recordExist():Boolean {
        var result=false
        dataStore.data.collect{
            result=(it[name1]!=null)
//            Log.d("resultVal",result.toString())
        }
        Log.d("resultVal",dataStore.data.toString())
        return result
    }

    val currScore: Flow<CurrScore> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e("DataStoreError", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        CurrScore(
            id=0,
            name1=preferences[name1]?:"",
            name2 = preferences[name2] ?:"",
            matchWon1=preferences[matchesWon1]?:0,
            matchWon2 = preferences[matchesWon2]?:0,
            draw = preferences[draw]?:0

        )
    }
}