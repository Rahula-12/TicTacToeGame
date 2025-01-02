package com.example.tictactoe.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.ui.navigation.TicTacToeApp
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var viewModel: TicTacToeViewModel

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel:TicTacToeViewModel=hiltViewModel()
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeApp(
                        viewModel,
                        logOut = {
                            finish()
                            googleSignInClient.signOut()
                            FirebaseAuth.getInstance().signOut()
                        }
                    )
                }
            }
        }
    }

}