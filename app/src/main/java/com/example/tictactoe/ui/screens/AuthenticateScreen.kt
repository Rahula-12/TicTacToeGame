package com.example.tictactoe.ui.screens

import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50100
import com.example.tictactoe.ui.theme.DeepOrange50200
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.DeepOrange50900
import com.example.tictactoe.ui.theme.LightBlue
import com.example.tictactoe.ui.theme.Pink40
import com.example.tictactoe.ui.theme.Pink80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AuthenticateScreen(
    modifier: Modifier = Modifier,
    signIn:(String,String)->Unit={ _,_->},
    register:(String,String)->Unit={ _,_-> },
    onSignInWithGoogleClicked:()->Unit={}
) {
    val context = LocalContext.current
    val noNetwork= rememberSaveable {
        mutableStateOf(false)
    }
    val networkDialog= rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope= rememberCoroutineScope()
    val connectivityManager=context.getSystemService(ConnectivityManager::class.java)
    val networkCallback=object:ConnectivityManager.NetworkCallback() {
        override fun onUnavailable() {
            super.onUnavailable()
            noNetwork.value=true
            coroutineScope.launch {
                while(noNetwork.value) {
                    networkDialog.value=true
                    delay(5000)
                }

            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            noNetwork.value=true
            coroutineScope.launch {
                while(noNetwork.value) {
                    networkDialog.value=true
                    delay(5000)
                }

            }
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            noNetwork.value=false
        }
    }
    if(networkDialog.value) {
        NoInternetDialog {
            networkDialog.value=false
        }
    }
    DisposableEffect(Unit) {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tic Tac Toe",
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
                colors = TopAppBarDefaults.smallTopAppBarColors(DeepOrange50300)
            )
        }
    ) { it ->
        var email: String by rememberSaveable {
            mutableStateOf("")
        }
        var password: String by rememberSaveable {
            mutableStateOf("")
        }
        var isLogging: Boolean by rememberSaveable {
            mutableStateOf(false)
        }
        Box(
            modifier = modifier.padding(it).fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(state = ScrollState(0))
            ) {
                OutlinedTextField(
                    placeholder = {
                        Text(
                            text = "Enter email",
                            fontFamily = FontFamily(Font(R.font.kalam_bold)),
                            fontSize = TextUnit(25f, TextUnitType.Sp),
                            color = Color.DarkGray
                        )
                    },
                    value = email,
                    onValueChange = { value ->
                        email = value
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.kalam_bold)
                        ),
                        fontSize = TextUnit(25f, TextUnitType.Sp),
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
//                            top = 200.dp
                        )
                        .border(
                            width = 4.dp, color = Pink40
                        )
                        .background(DeepOrange50900),
                )
                OutlinedTextField(
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Enter password",
                            fontFamily = FontFamily(Font(R.font.kalam_bold)),
                            fontSize = TextUnit(25f, TextUnitType.Sp),
                            color = Color.DarkGray
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.kalam_bold)
                        ),
                        fontSize = TextUnit(25f, TextUnitType.Sp),
                    ),
                    value = password,
                    onValueChange = { value ->
                        password = value
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 30.dp
                        )
                        .border(
                            width = 4.dp, color = Pink40
                        )
                        .background(DeepOrange50200),
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            start = 10.dp,
                            end = 10.dp
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DeepOrange50300),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if (email.isEmpty()) Toast.makeText(
                            context,
                            "Please enter email",
                            Toast.LENGTH_SHORT
                        ).show()
                        else if (password.isEmpty()) Toast.makeText(
                            context,
                            "Please enter password",
                            Toast.LENGTH_SHORT
                        ).show()
                        else {
                            if (!isLogging) {
                                register(email,password)
                            } else {
                                signIn(email,password)
                            }
                        }
                    }) {
                    Text(
                        text = if (!isLogging) "Register" else "Login",
                        fontFamily = FontFamily(Font(R.font.kalam_bold)),
                        fontSize = TextUnit(25f, TextUnitType.Sp),
                        color = Color.DarkGray
                    )
                }
                Row {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = TextUnit(15f, TextUnitType.Sp),
                                    color = DeepOrange50100,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = FontFamily(
                                        Font(R.font.kalam_bold),
                                    )
                                )
                            ) {
                                append(text = if (!isLogging) "Already have an account? Please " else "New User? Please ")
                            }
                        },
                        modifier = modifier.padding(
                            top = 15.dp
                        ),
                        textAlign = TextAlign.Center
                    )
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = LightBlue,
                                    fontSize = TextUnit(15f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                                )
                            ) {
                                append(text = if (!isLogging) "Login" else "Register")
                            }
                        },
                        modifier = modifier.padding(
                            top = 15.dp
                        )
                    ) {
                        isLogging = !isLogging
                    }
                }
                Text(
                    text = "Or",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    fontSize = TextUnit(25f, TextUnitType.Sp),
                    color = Pink80,
                    modifier = modifier.padding(
                        top = 20.dp
                    )
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 30.dp
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DeepOrange50),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onSignInWithGoogleClicked()
                    }) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_sign),
                            contentDescription = "google symbol",
                            modifier = modifier.padding(
                                end = 10.dp
                            )
                        )
                        Text(
                            text = "Login with Google",
                            fontFamily = FontFamily(Font(R.font.kalam_bold)),
                            fontSize = TextUnit(25f, TextUnitType.Sp),
                            color = Color.DarkGray
                        )
                    }

                }
            }
        }
    }

}