package com.example.tictactoe.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.model.User
import com.example.tictactoe.data.repository.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playersRepository: PlayersRepository):ViewModel() {

    val playersList: StateFlow<List<User>> = playersRepository.userList

    val invited:StateFlow<String> = playersRepository.invited

    init {
        Log.d("usersInViewModel",playersList.value.toString())
    }

    fun declineInvite() = playersRepository.declineInvite()

    fun acceptInvite(senderEmail:String) = playersRepository.acceptInvite(senderEmail)

    fun sendInvite(selectedPlayer:String) = playersRepository.sendInvite(selectedPlayer)

}