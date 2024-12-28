package com.example.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.Red50200

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ModesScreen(
    modifier: Modifier=Modifier,
    offlineModeSelected:()->Unit={},
    onlineModeSelected:()->Unit={},
    onBackPressed:()->Unit={}
) {
//    FirebaseFirestore.getInstance().collection("users")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text="Select Modes",
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
                        painter = painterResource(id = R.drawable.baseline_logout_24),
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
    ) {it->
        Box(
            modifier=modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription ="background",
                modifier=modifier.fillMaxSize(),
                contentScale=ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier
                    .padding(
                        top = 90.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                ,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        offlineModeSelected()
                    },
                    modifier= modifier
                        .fillMaxWidth(),
                    colors= ButtonDefaults.buttonColors(
                         containerColor = Red50200
                    )
                ) {
                    Row(
                        modifier=modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.offline),
                            modifier=modifier.size(100.dp),
                            contentDescription = "offline",
                            tint = Brown50600
                        )
                        Text(
                            text = "Offline\n\n Mode",
                            fontFamily = FontFamily(
                                Font(R.font.kalam_bold,
                                    FontWeight.Bold
                                )
                            ),
                            color = Brown50600,
                            fontSize = TextUnit(50f, TextUnitType.Sp),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                }
                Button(
                    onClick = {
                        onlineModeSelected()
                    },
                    modifier= modifier
                        .padding(
                            top = 50.dp
                        )
                        .fillMaxWidth(),
                    colors= ButtonDefaults.buttonColors(
                        containerColor = DeepOrange50300
                    )
                ) {
                    Row(
                        modifier=modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_online_24),
                            modifier=modifier.size(100.dp),
                            contentDescription = "offline",
                            tint = Color.DarkGray
                        )
                        Text(
                            text = "Online\n\n Mode",
                            color = Color.DarkGray,
                            fontFamily = FontFamily(
                                Font(R.font.kalam_bold,
                                    FontWeight.Bold
                                )
                            ),
                            fontSize = TextUnit(50f, TextUnitType.Sp),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }

}