package com.example.tictactoe.ui.screens

import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tictactoe.R
import com.example.tictactoe.data.model.Match
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineGameScreen(
    modifier: Modifier = Modifier,
    matchId: String = "",
    navController: NavController
) {
    val moveBackDialog= rememberSaveable {
        mutableStateOf(false)
    }
    val viewModel: OnlineGameViewModel = hiltViewModel()
    val matchState = viewModel.matchState.collectAsStateWithLifecycle()
    if(moveBackDialog.value) {
        MoveBackDialog(moveBackDialog = moveBackDialog, matchState = matchState, viewModel = viewModel)
    }
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
                                moveBackDialog.value = true
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
        val currentUser=viewModel.currentUser.collectAsStateWithLifecycle()
        val timer = rememberSaveable(inputs= arrayOf(matchState.value.turn)) {
            mutableStateOf(10)
        }
        LaunchedEffect(key1 = currentUser.value.matchId) {
            delay(1000)
            if(currentUser.value.matchId=="") {
                viewModel.removeMatch()
                navController.popBackStack()
//                moveBackDialog.value=true
            }
        }
        LaunchedEffect(key1 = true) {
            withContext(Dispatchers.IO) {
                viewModel.fetchMatchState(matchId)
            }
        }
        LaunchedEffect(key1 = timer.value) {
            if(timer.value==0) {
                if(matchState.value.winner=="") {
                if(matchState.value.turn==0 && matchState.value.player1Id==FirebaseAuth.getInstance().currentUser?.email) {
                    viewModel.updateMatch(winner = matchState.value.player2Id,matchId=matchState.value.matchId)
                }
                else if(matchState.value.turn==1 && matchState.value.player2Id==FirebaseAuth.getInstance().currentUser?.email) {
                    viewModel.updateMatch(winner = matchState.value.player1Id,matchId=matchState.value.matchId)
                }
                    }
            }
            else {
                delay(1000)
                timer.value-=1
            }
        }
        val showDialog= rememberSaveable {
            mutableStateOf(false)
        }
        if(matchState.value.winner!="") {
            showDialog.value=true
        }
        if(showDialog.value && matchState.value.winner!="") {
            ShowWinner(
                showDialog,
                winningStatement =
                    if(matchState.value.winner==FirebaseAuth.getInstance().currentUser?.email) {
                        "Congratulations you won 🎉"
                    }
                    else if(matchState.value.winner!="Draw") {
                        "Damn! You lost 😢"
                    }
                    else {
                        "Game Draw"
                    },
                matchState=matchState,
                playAgain = {playing,matchId->
                    viewModel.playAgain(playing,matchId)
                },
                resetMatch={viewModel.resetMatch(matchId)},
                resetUser = {
                    viewModel.resetUser()
                }
            )
        }
//        LaunchedEffect(key1 = matchState.value.winner) {
//            delay(2000)
//            viewModel.removeMatch(matchId)
//        }
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
                        text = if(matchState.value.winner=="") {
                            when (matchState.value.turn) {
                                0 -> timer.value.toString()
                                else -> ""
                            }
                        }
                        else "",
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
                        text = if(matchState.value.winner=="") {
                            when (matchState.value.turn) {
                                1 -> timer.value.toString()
                                else -> ""
                            }
                        }
                        else "",
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

@Composable
fun ShowWinner(
    showDialog: MutableState<Boolean>,
    winningStatement: String,
    matchState: State<Match>,
    playAgain: (Boolean, String) -> Unit = { _, _ -> },
    resetMatch: () -> Unit = {},
    resetUser: () -> Unit
) {
    val context= LocalContext.current
    val countDown= rememberSaveable {
        mutableIntStateOf(10)
    }
    val showTimer= rememberSaveable {
        mutableStateOf(false)
    }
    if(showTimer.value) {
        TimerDialog(countDown = countDown)
    }
    LaunchedEffect(key1 = countDown.intValue) {
        if(countDown.intValue<=0) {
            resetMatch()
            resetUser()
            showDialog.value=false
        }
        delay(1000)
        countDown.intValue-=1
    }
    LaunchedEffect(matchState.value.playAgain1,matchState.value.playAgain2) {
        if(matchState.value.playAgain1!=-1 || matchState.value.playAgain2!=-1) {
            if(matchState.value.playAgain1==1 && matchState.value.playAgain2==1) {
                resetMatch()
                showDialog.value=false
            }
            else if(matchState.value.playAgain1==0 || matchState.value.playAgain2==0) {
                resetUser()
                resetMatch()
                Toast.makeText(context,"Game Cancelled",Toast.LENGTH_SHORT).show()
                showDialog.value=false
            }
        }
    }
    AlertDialog(
        dismissButton={
            TextButton(onClick = {
                //Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
                playAgain(false,matchState.value.matchId)
                showDialog.value=false
            }) {
                Text(
                    text = "No",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        onDismissRequest = {
            playAgain(false,matchState.value.matchId)
            showDialog.value=false
        },
        confirmButton = {
            TextButton(onClick = {
                // Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
//                sendInvite()
                showTimer.value=true
                playAgain(true,matchState.value.matchId)
                //timer.value=10
                showDialog.value=false
            }) {
                Text(
                    text = "Yes",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        title={
            Text(
                text = "Winner",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(25f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        text = {
            Text(
                text = "$winningStatement. Want to play again?\n\nPlease respond in ${countDown.intValue} secs.",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        containerColor = DeepOrange50300
    )
}

@Composable
fun TimerDialog(countDown:MutableState<Int>) {
    AlertDialog(
        dismissButton={
        },
        onDismissRequest = {

        },
        confirmButton = {

        },
        title={

        },
        text = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle()){
                        append("Your invite should be accepted in \n\n")
                    }
                    withStyle(style = SpanStyle(
                        fontSize = TextUnit(30f, type = TextUnitType.Sp)
                    )
                    ){
                        append("${countDown.value} s")
                    }
                },
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        containerColor = DeepOrange50300
    )
}

@Composable
fun MoveBackDialog(
    moveBackDialog:MutableState<Boolean>,
    matchState: State<Match>,
    viewModel: OnlineGameViewModel
) {
    AlertDialog(
        dismissButton={
            TextButton(onClick = {
                //Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
                moveBackDialog.value=false
            }) {
                Text(
                    text = "No",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        onDismissRequest = {
            moveBackDialog.value=false
        },
        confirmButton = {
            TextButton(onClick = {
                if(matchState.value.player1Id==FirebaseAuth.getInstance().currentUser?.email) {
                    viewModel.updateMatch(winner = matchState.value.player2Id,matchId=matchState.value.matchId)
                }
                else {
                    viewModel.updateMatch(winner = matchState.value.player1Id,matchId=matchState.value.matchId)
                }
            }) {
                Text(
                    text = "Yes",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        title={
            Text(
                text = "Warning",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(25f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        text = {
            Text(
                text = "Your will lose the match. Do you want to leave?",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        containerColor = DeepOrange50300
    )
}

