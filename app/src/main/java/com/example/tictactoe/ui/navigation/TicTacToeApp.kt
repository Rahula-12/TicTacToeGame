package com.example.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.example.tictactoe.ui.screens.GameScreen
import com.example.tictactoe.ui.screens.HomeScreen

enum class Screen{
    HomeScreen,GameScreen
}
@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel,logOut:()->Unit={}) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ){
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
    }
}
