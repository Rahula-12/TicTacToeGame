package com.example.tictactoe.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.repository.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
):ViewModel() {
    fun register() = playersRepository.registerUser()


}