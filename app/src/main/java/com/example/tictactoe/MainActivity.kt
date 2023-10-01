package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.data.ScoreDataBase
import com.example.tictactoe.ui.TicTacToeApp
import com.example.tictactoe.ui.TicTacToeViewModel
import com.example.tictactoe.ui.TicTacToeViewModelFactory
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val factory = TicTacToeViewModelFactory(ScoreDataBase.getDatabase(this).currScoreDao())
                    val viewModel = ViewModelProvider(this, factory)[TicTacToeViewModel::class.java]
                    TicTacToeApp(viewModel)
                }
            }
        }
    }
}