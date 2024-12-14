package com.example.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tictactoe.R
import com.example.tictactoe.ui.board.GameBoard
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50100
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.Pink80
import com.example.tictactoe.ui.theme.PurpleGrey40
import com.example.tictactoe.ui.viewmodel.OnlineGameViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineGameScreen(
    modifier: Modifier = Modifier,
    matchId: String = ""
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Arena",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
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
                                // onBackButtonClick()
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
        val timer = rememberSaveable {
            mutableStateOf(11)
        }
        val viewModel: OnlineGameViewModel = hiltViewModel()
        val matchState = viewModel.matchState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = true) {
            withContext(Dispatchers.IO) {
                viewModel.fetchMatchState(matchId)
            }
        }
        LaunchedEffect(key1 = matchState.value.winner) {
            delay(2000)
            viewModel.removeMatch(matchId)
        }
//        viewModel.fetchMatchState(matchId)
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
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = matchState.value.player1Id,
                        modifier = modifier
                            .background(DeepOrange50),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = Brown50600,
                        fontSize = TextUnit(25f, TextUnitType.Sp)
                    )
                    Text(
                        text = "0",
                        modifier = modifier
                            .background(
                                Pink80
                            )
                            .width(50.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = PurpleGrey40,
                        fontSize = TextUnit(25f, TextUnitType.Sp)
                    )
                }
                Box(
                    modifier = Modifier
//                        .padding(top=20.dp, bottom = 20.dp)
                        .fillMaxSize(0.9f)
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
                    GameBoard(direction = matchState.value.direction)
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
                                    val state = when (i) {
                                        0 -> matchState.value.firstRow[j]
                                        1 -> matchState.value.secondRow[j]
                                        else -> matchState.value.thirdRow[j]
                                    }
                                    Image(
                                        painter = painterResource(
                                            id = when (state) {
                                                0 -> R.drawable.circle
                                                1 -> R.drawable.cross3
                                                else -> R.drawable.ic_launcher_foreground
                                            }
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clickable {
                                                if (matchState.value.turn == 0) {
                                                    if (FirebaseAuth.getInstance().currentUser?.email == matchState.value.player1Id) {
                                                        viewModel.assignValue(i, j, matchId)
                                                    }
                                                } else {
                                                    if (FirebaseAuth.getInstance().currentUser?.email == matchState.value.player2Id) {
                                                        viewModel.assignValue(i, j, matchId)
                                                    }
                                                }
                                            }
                                            .weight(1f)
                                            .fillMaxSize(),
                                        colorFilter = when (state) {
                                            0 -> ColorFilter.tint(Color.Red)
                                            1 -> ColorFilter.tint(Color.Red)
                                            else -> ColorFilter.tint(Color.Transparent)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = matchState.value.player2Id,
                        modifier = modifier
                            .background(DeepOrange50),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = Brown50600,
                        fontSize = TextUnit(25f, TextUnitType.Sp)
                    )
                    Text(
                        text = "0",
                        modifier = modifier
                            .background(
                                Pink80
                            )
                            .width(50.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = PurpleGrey40,
                        fontSize = TextUnit(25f, TextUnitType.Sp)
                    )
                }
            }
        }
    }
}
