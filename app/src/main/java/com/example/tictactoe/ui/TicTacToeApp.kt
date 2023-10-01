package com.example.tictactoe.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen{
    HomeScreen,GameScreen
}
@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel) {
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
                viewModel = viewModel
            )
        }
        composable(Screen.GameScreen.name) {
            GameScreen(
                viewModel = viewModel,
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
