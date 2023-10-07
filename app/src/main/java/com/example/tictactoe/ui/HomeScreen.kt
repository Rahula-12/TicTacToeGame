package com.example.tictactoe.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50200
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.DeepOrange50700
import com.example.tictactoe.ui.theme.DeepOrange50900
import com.example.tictactoe.ui.theme.Orange50400
import com.example.tictactoe.ui.theme.Red50100
import com.example.tictactoe.ui.theme.Red50200
import com.example.tictactoe.ui.theme.Red50600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TicTacToeViewModel,
    modifier:Modifier=Modifier,
    onNextClick:()->Unit={}
){
    val gameState=viewModel.gameState.collectAsState().value
    if(gameState.emptyName) {
        ShowAlert({ viewModel.showAlert(0) },0)
    }
    if(gameState.sameName) {
        ShowAlert({ viewModel.showAlert(1) },1)
    }
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
                    .verticalScroll(rememberScrollState())
            ) {
                    Text(
                        text = "Tic Tac Toe",
                        textAlign = TextAlign.Center,
                        modifier= Modifier
                            .padding(
                                top = 50.dp
                            )
                            .fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.kalam_bold,FontWeight.Bold)),
                        fontSize= TextUnit(55f,type= TextUnitType.Sp),
                        color = DeepOrange50
                    )
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
                                top = 80.dp
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
                                    .fillMaxWidth(0.2f)
                                    .padding(
                                        top=10.dp
                                    )
//                                    .weight(1f)
                            )
                            TextField(
                                value = name1,
                                onValueChange = {
                                    name1=it
                                },
                                modifier = Modifier
//                                    .weight(2.5f)
                                    .border(width = 0.dp, color = Color.Transparent)
                                    .background(color = Color.Transparent)
                                    .width(1000.dp),
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
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(
                                top = 70.dp
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
                                modifier = Modifier.fillMaxWidth(0.2f).padding(
                                    top=10.dp
                                )
                            )
                            TextField(
                                value = name2,
                                onValueChange = {
                                    name2=it
                                },
                                modifier = Modifier
//                                    .weight(2.5f)
                                    .border(width = 0.dp, color = Color.Transparent)
                                    .background(color = Color.Transparent)
                                    .width(1000.dp),
                                placeholder = {
                                    Text(
                                        text = "Enter your name",
                                        fontFamily = FontFamily(Font(R.font.kalam_bold))
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
                    Button(
                        onClick = {
                            name1=""
                            name2=""
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
                    fontFamily = FontFamily(Font(R.font.kalam_bold))
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
                fontSize = TextUnit(20f, type = TextUnitType.Sp)
            )
        }
    )
}


//@Composable
//@Preview(showSystemUi = true, showBackground = true)
//fun HomeScreenPreview(){
//    HomeScreen()
//}

