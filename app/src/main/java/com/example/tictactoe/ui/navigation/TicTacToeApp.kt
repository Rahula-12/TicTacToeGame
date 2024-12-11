package com.example.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.example.tictactoe.ui.screens.GameScreen
import com.example.tictactoe.ui.screens.HomeScreen
import com.example.tictactoe.ui.screens.ModesScreen
import com.example.tictactoe.ui.screens.OnlineGameScreen
import com.example.tictactoe.ui.screens.SelectPlayerScreen
import com.google.firebase.auth.FirebaseAuth

enum class Screen{
    ModesScreen,HomeScreen,GameScreen,SelectPlayerScreen,OnlineGameScreen
}
@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel,logOut:()->Unit={}) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ModesScreen.name
    ){
        composable(Screen.ModesScreen.name) {
            ModesScreen(
                offlineModeSelected = {
                    navController.navigate(Screen.HomeScreen.name)
                },
                onBackPressed = {
                    navController.popBackStack()
                },
                onlineModeSelected = {
                    navController.navigate(Screen.SelectPlayerScreen.name)
                }
            )
        }
        composable(Screen.SelectPlayerScreen.name){
            SelectPlayerScreen(
                photoUri = FirebaseAuth.getInstance().currentUser?.photoUrl,
                currentUserEmail = FirebaseAuth.getInstance().currentUser?.email?:"",
                moveToGameScreen = {matchId->
                    navController.navigate(Screen.OnlineGameScreen.name+"/$matchId")
                }
            )
        }
        composable(Screen.HomeScreen.name){
            HomeScreen(
                onNextClick = {
//                    viewModel.reset(0)
                    navController.navigate(Screen.GameScreen.name)
                },
                logOut=logOut,
                viewModel = viewModel
            )
        }
        composable(Screen.GameScreen.name) {
            GameScreen(
                viewModel = viewModel,
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.OnlineGameScreen.name+"/{matchId}") {navBackStack->
            val matchId=navBackStack.arguments?.getString("matchId")
            matchId?.let {
                OnlineGameScreen(matchId = it)
            }
        }
    }
}
