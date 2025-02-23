package com.example.tictactoe.ui.screens

import android.widget.Toast
import androidx.compose.material.icons.filled.ArrowBack
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50200
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.DeepOrange50700
import com.example.tictactoe.ui.theme.DeepOrange50900
import com.example.tictactoe.ui.theme.Orange50400
import com.example.tictactoe.ui.theme.Pink40
import com.example.tictactoe.ui.theme.Red50100
import com.example.tictactoe.ui.theme.Red50200
import com.example.tictactoe.ui.theme.Red50600
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TicTacToeViewModel,
    modifier:Modifier=Modifier,
    onNextClick:()->Unit={},
    onBackPressed:()->Unit={}
){
    val gameState=viewModel.gameState.collectAsState().value
    val tempCheck= rememberSaveable {
        mutableStateOf(true)
    }
    val context= LocalContext.current
    if(!gameState.prevRecord && tempCheck.value){
        Toast.makeText(context,"No previous matches found.",Toast.LENGTH_SHORT).show()
        tempCheck.value=false
    }
    if(gameState.emptyName) {
        ShowAlert({ viewModel.showAlert(0) },0)
    }
    if(gameState.sameName) {
        ShowAlert({ viewModel.showAlert(1) },1)
    }
    Scaffold(
            topBar = {
                    TopAppBar(
                        title = {
                                Text(
                                    text="Tic Tac Toe",
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
                                        onBackPressed()
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
        ){it->
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(
                    id = R.drawable.background
                ),
                contentDescription = "background",
                modifier= Modifier
                    .matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier= Modifier
//                    .matchParentSize()
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
            ) {
                var name1 by rememberSaveable {
                    mutableStateOf("")
                }
                var name2 by rememberSaveable {
                    mutableStateOf("")
                }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(
                                top = 80.dp,
                                start = 5.dp,
                                end = 5.dp
                            )
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                            Text(
                                text = "PlayerO:",
                                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                                fontSize = TextUnit(30f,type=TextUnitType.Sp),
                                color= DeepOrange50900,
                                modifier = Modifier
                                    .fillMaxWidth(0.25f)
                                    .padding(
                                        // top=10.dp,
                                        start = 5.dp
                                    )
                                    .background(DeepOrange50200)
                                    .border(width = 4.dp, color = Brown50600)
                                    .padding(4.dp)
                                    .weight(1.2f)
                            )
                            OutlinedTextField(
                                value = name1,
                                onValueChange = {
                                    name1=it
                                },
                                modifier = Modifier
                                    .weight(2.5f)
                                    .border(width = 4.dp, color = Pink40)
                                    .padding(
                                        start = 5.dp
                                    )
                                    .background(color = DeepOrange50900),
                                placeholder = {
                                    Text(
                                        text = "Enter your name",
                                        fontFamily = FontFamily(Font(R.font.kalam_bold))
                                    )
                                },
                                trailingIcon = {
                                    Image(painter = painterResource(
                                        id = R.drawable.empty_circle_svgrepo_com
                                    ),
                                        contentDescription = "circle",
                                        modifier=Modifier.size(20.dp))
                                }
                            )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(
                                top = 70.dp,
                                start = 5.dp,
                                end = 5.dp
                            )
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                            Text(
                                text = "PlayerX:",
                                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                                fontSize = TextUnit(30f,type=TextUnitType.Sp),
                                color= DeepOrange50200,
                                modifier = Modifier
                                    .fillMaxWidth(0.25f)
                                    .padding(
                                        // top=10.dp,
                                        start = 5.dp,
                                        //end=5.dp
                                    )
                                    .background(DeepOrange50900)
                                    .border(width = 4.dp, color = Brown50600)
                                    .padding(4.dp)
                                    .weight(1.2f)
                            )
                            OutlinedTextField(
                                value = name2,
                                onValueChange = {
                                    name2=it
                                },
                                modifier = Modifier
                                    .weight(2.5f)
                                    .border(width = 4.dp, color = Pink40)
                                    .padding(
                                        start = 5.dp
                                    )
                                    .background(color = DeepOrange50200)
                                    ,
                                placeholder = {
                                    Text(
                                        text = "Enter your name",
                                        fontFamily = FontFamily(Font(R.font.kalam_bold)),
                                        color = Color.DarkGray
                                    )
                                },
                                trailingIcon = {
                                    Image(painter = painterResource(
                                        id = R.drawable.cross3
                                    ),
                                        contentDescription = "cross",
                                        modifier=Modifier.size(20.dp))
                                }
                            )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()){
                        Button(
                            onClick = {
                                if(name1.isNotEmpty() && name2.isNotEmpty()) {
                                    if(name1==name2) viewModel.showAlert(1)
                                    else if(gameState.prevRecord) {
                                        viewModel.reset(0, name1, name2)
                                        name1=""
                                        name2=""
                                        onNextClick()
                                    }
                                    else {
                                        viewModel.insertRecord(name1, name2)
                                        name1=""
                                        name2=""
                                        onNextClick()
                                    }
                                }
                                else {
                                    viewModel.showAlert(0)
                                }
                            },
                            modifier= Modifier
                                .padding(
                                    top = 70.dp
                                )
                                .wrapContentSize()
                                .align(Alignment.Center)
                            ,
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Red50600),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                text = "New Match",
                                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                                fontSize = TextUnit(25f,TextUnitType.Sp),
                                color=Red50100

                            )
                        }
                    }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()){
                    val context= LocalContext.current
                    Button(
                        onClick = {
                                name1 = ""
                                name2 = ""
                                onNextClick()
                        },
                        modifier= Modifier
                            .padding(
                                top = 50.dp,
                                bottom = 50.dp
                            )
                            .wrapContentSize()
                            .align(Alignment.Center)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange50400,
                            contentColor=DeepOrange50700,
                            disabledContainerColor=Red50200,
                            disabledContentColor=Red50100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = gameState.prevRecord
                    ) {
                        Text(
                            text = "Prev Match",
                            fontFamily = FontFamily(Font(R.font.kalam_bold)),
                            fontSize = TextUnit(25f,TextUnitType.Sp),
//                            color= Brown50400
                        )
                    }
                }
            }
        }
        }
}

@Composable
fun ShowAlert(onDismissRequest: () -> Unit,check:Int) {
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
                    color=Color.DarkGray
                )
            }
        },
        text = {
            Text(
                text = if(check==0) "PlayerO and PlayerX can't be empty."
                else "PlayerO and PlayerX can't be same.",
                modifier=Modifier
                    .wrapContentSize()
                ,
                textAlign = TextAlign.Left,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color=Color.DarkGray
            )
        }
    )
}


//@Composable
//@Preview(showSystemUi = true, showBackground = true)
//fun HomeScreenPreview(){
//    HomeScreen()
//}

