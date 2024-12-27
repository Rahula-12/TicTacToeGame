package com.example.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.tictactoe.R
import com.example.tictactoe.ui.board.GameBoard
import com.example.tictactoe.ui.viewmodel.GameState
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50100
import com.example.tictactoe.ui.theme.DeepOrange50200
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.DeepOrange50400
import com.example.tictactoe.ui.theme.DeepOrange50900
import com.example.tictactoe.ui.theme.Pink40

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameScreen(
    viewModel: TicTacToeViewModel,
    onBackButtonClick:()->Unit={}
) {
        FixedGrid(viewModel,onBackButtonClick)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedGrid(
    viewModel: TicTacToeViewModel,
    onBackButtonClick:()->Unit={}
) {
    val gameState: GameState =viewModel.gameState.collectAsState().value
    if(gameState.winnerFound!=-1) {
        WinnerAlert({viewModel.reset(1)},
            winner = gameState.winnerFound,
            name = when(gameState.winnerFound){
                0->""
                1->gameState.name1
                else->gameState.name2
            }
        )
    }
    Scaffold(
        topBar = {
                TopAppBar(
                    title = {
                            Text(
                                text="Arena",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(
                                    Font(R.font.kalam_bold,
                                    FontWeight.Bold
                                )
                                ),
                                fontSize = TextUnit(50f, TextUnitType.Sp),
                                modifier = Modifier
                                    .fillMaxWidth(),
//                                    .background(DeepOrange50200)
                                color = Brown50600
                            )
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier
                                .clickable {
                                    onBackButtonClick()
                                }
                                .size(40.dp)
//                                .background(DeepOrange50200)
                                .fillMaxSize(),
                            tint = Brown50600
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(DeepOrange50300)
                )
        }
    ) { it ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(
                            top = 100.dp
                        )
                        .aspectRatio(1f)
                        .background(DeepOrange50100)
//                        .align(Alignment.TopCenter)
//                    .shadow(
//                        elevation = 10.dp,
//                        shape = RoundedCornerShape(20.dp)
//                    )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    GameBoard(direction = gameState.direction)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
//                        modifier = Modifier.height(600.dp)
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                    ) {
                        for (i in 0..2) {

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .horizontalScroll(
                                        rememberScrollState()
                                    )
                            ) {
                                for (j in 0..2) {
                                    Image(
                                        painter = painterResource(
                                            id = when (gameState.visited[i][j]) {
                                                0 -> R.drawable.circle
                                                1 -> R.drawable.cross3
                                                else -> R.drawable.ic_launcher_foreground
                                            }
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clickable {
                                                viewModel.assignValue(i, j)
                                            }
                                            .weight(1f)
                                            .fillMaxSize(),
                                        colorFilter = when (gameState.visited[i][j]) {
                                            0 -> ColorFilter.tint(Color.Red)
                                            1 -> ColorFilter.tint(Color.Red)
                                            else -> ColorFilter.tint(Color.Transparent)
                                        }
//                                            .height(100.dp)
//                                            .width(100.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 50.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(DeepOrange50200)
                            .weight(1f)
                            .border(width = 4.dp, color = Pink40)
                    ){
                        Text(
                            text = "${gameState.name1}:${gameState.matchesWon1}",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(30f,TextUnitType.Sp),
                            fontFamily = FontFamily(Font(R.font.kalam_bold,FontWeight.Bold)),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .background(DeepOrange50300)
                            .weight(1f)
                            .border(width = 4.dp, color = Pink40)
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            )
                    ){
                        Text(
                            text = "Draw:${gameState.draw}",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(30f,TextUnitType.Sp),
                            fontFamily = FontFamily(Font(R.font.kalam_bold,FontWeight.Bold)),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .background(DeepOrange50900)
                            .weight(1f)
                            .border(width = 4.dp, color = Pink40)
                    ){
                        Text(
                            text = "${gameState.name2}:${gameState.matchesWon2}",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(30f,TextUnitType.Sp),
                            fontFamily = FontFamily(Font(R.font.kalam_bold,FontWeight.Bold)),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Button(
                    onClick = {
                        viewModel.reset(1)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = 60.dp
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DeepOrange50),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Play Again",
                        fontFamily = FontFamily(Font(R.font.kalam_bold)),
                        fontSize = TextUnit(25f,TextUnitType.Sp),
                        color= Color.DarkGray
                    )
                }
            }
            }
        }
    }

@Composable
fun WinnerAlert(onDismissRequest: () -> Unit,winner:Int,name:String) {
    AlertDialog(
        containerColor = DeepOrange50300,
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    "OK",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        text = {
            Text(
                text = when(winner) {
                    0 -> "Game Draw! Better luck next time 🙂"
                    else -> "$name wins 🎉"

                },
                modifier=Modifier
                  //  .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                ,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        }
    )
}

