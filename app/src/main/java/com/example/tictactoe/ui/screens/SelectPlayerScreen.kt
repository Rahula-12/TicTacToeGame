package com.example.tictactoe.ui.screens

//import coil3.compose.AsyncImage
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tictactoe.R
import com.example.tictactoe.data.model.User
import com.example.tictactoe.ui.theme.Brown50400
import com.example.tictactoe.ui.theme.Brown50600
import com.example.tictactoe.ui.theme.DeepOrange150900
import com.example.tictactoe.ui.theme.DeepOrange50
import com.example.tictactoe.ui.theme.DeepOrange50100
import com.example.tictactoe.ui.theme.DeepOrange50200
import com.example.tictactoe.ui.theme.DeepOrange50300
import com.example.tictactoe.ui.theme.Pink40
import com.example.tictactoe.ui.theme.Pink80
import com.example.tictactoe.ui.theme.PurpleGrey40
import com.example.tictactoe.ui.theme.Red50600
import com.example.tictactoe.ui.viewmodel.PlayersViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SelectPlayerScreen(
    modifier: Modifier = Modifier,
    photoUri: Uri? = null,
    currentUserEmail: String = "rahul@arora.com",
    moveToGameScreen:(matchId:String)->Unit={}
) {
    val viewModel:PlayersViewModel = hiltViewModel()
    DisposableEffect(key1 = true) {
        viewModel.markOnline()
        onDispose {
            viewModel.unmarkOnline()
        }
    }
    val users=viewModel.playersList.collectAsStateWithLifecycle()
    val invited=viewModel.invited.collectAsStateWithLifecycle()
    val matchId=viewModel.matchId.collectAsStateWithLifecycle()
    LaunchedEffect(matchId.value) {
        if(matchId.value!="") {
            moveToGameScreen(matchId.value)
        }
    }
//    Log.d("users",users.value.toString())
    var currUser: User?=null
    for(user in users.value) {
        if(user.emailId==FirebaseAuth.getInstance().currentUser!!.email) {
            currUser=user
            break
        }
    }
    val playerList:MutableMap<String,Int> = mutableMapOf()
    currUser?.let {
//        playerList.putAll(it.totalMatches)
        for(user in users.value) {
            if(user==it) continue
            if(!it.totalMatches.contains(user.emailId)) {
                playerList[user.emailId] = 0
            }
            else {
                playerList[user.emailId]= it.totalMatches[user.emailId] as Int
            }
        }
    }
    val context= LocalContext.current
    if(invited.value!="") {
        if(invited.value=="Accepted Invite") {
//            moveToGameScreen()
            Toast.makeText(context,"Invite was accepted.",Toast.LENGTH_SHORT).show()
        }
        else if(invited.value=="Declined Invite") {
            Toast.makeText(context,"Invite was declined.",Toast.LENGTH_SHORT).show()
        }
        else {
            InvitedDialog(
                inviteSender = invited.value,
                declineInvite = { viewModel.declineInvite() }) {
//                moveToGameScreen()
                viewModel.acceptInvite(invited.value,currUser?.emailId?:"")
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select Player",
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
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = 50.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
//                    AsyncImage(model = photoUri, contentDescription = null)
                GlideImage(
                    model = photoUri,
                    contentDescription = null,
                    modifier = modifier
                        .size(200.dp)
                        .clipToBounds()
                        .clip(CircleShape)
                        .padding(
                            top = 100.dp
                        )
                )
                Text(
                    text = currentUserEmail,
                    modifier = modifier
                        .padding(
                            top = 10.dp
                        )
                        .background(
                            color = DeepOrange50200
                        )
                        .padding(
                            5.dp
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.kalam_bold,
                            FontWeight.Bold
                        )
                    ),
                    fontSize = TextUnit(30f, TextUnitType.Sp),
                    color = DeepOrange150900
                )
                Spacer(modifier = modifier.height(30.dp))
                CurrentStatusRow(
                    won=currUser?.matchesWon?:0,
                    lost = currUser?.matchesLost?:0,
                    draw = currUser?.matchesDraw?:0
                )
                Divider()
                UserAndMatchesPlayed(
                    usersAndMatches = playerList,
                    invited=invited.value
                )
            }
        }
    }
}

@Composable
fun CurrentStatusRow(
    won: Int = 0,
    lost: Int = 0,
    draw: Int = 0,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        CurrentStatus(
            matches = won,
            status = "Won",
            modifier = modifier.weight(1f),
            backgroundColor = DeepOrange50100,
            headingTextColor = Pink40,
            statusTextColor = Red50600
        )
        CurrentStatus(
            matches = lost,
            status = "Lost",
            modifier = modifier.weight(1f),
            backgroundColor = DeepOrange50300,
            headingTextColor = DeepOrange50100,
            statusTextColor = Brown50600
        )
        CurrentStatus(
            matches = draw,
            status = "Draw",
            modifier = modifier.weight(1f),
            backgroundColor = Brown50400,
            headingTextColor = DeepOrange50200,
            statusTextColor = Pink80
        )
    }
}

//@Preview
@Composable
fun CurrentStatus(
    modifier: Modifier = Modifier,
    matches: Int = 0,
    status: String = "Won",
    backgroundColor: Color = Color.Cyan,
    headingTextColor: Color = Color.Blue,
    statusTextColor: Color = Color.Yellow
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(backgroundColor)
            .padding(5.dp)
    ) {
        Text(
            text = status,
            color = headingTextColor,
            fontFamily = FontFamily(
                Font(
                    R.font.kalam_bold,
                    FontWeight.Bold
                )
            ),
            fontSize = TextUnit(30f, TextUnitType.Sp)
        )
        Text(
            text = matches.toString(),
            color = statusTextColor,
            fontFamily = FontFamily(
                Font(
                    R.font.kalam_bold,
                    FontWeight.Bold
                )
            ),
            fontSize = TextUnit(25f, TextUnitType.Sp)
        )
    }
}

@Composable
fun UserAndMatchesPlayed(
    usersAndMatches: Map<String, Int> = mapOf("abc@abc.com" to 4, "abc2@abc.com" to 3),
    modifier: Modifier = Modifier,
    invited:String
) {
    val viewModel:PlayersViewModel = hiltViewModel()
    val showDialog=rememberSaveable {
        mutableStateOf(false)
    }
    if(invited=="Declined Invite" || invited=="Accepted Invite") {
        showDialog.value=false
    }
    var index= rememberSaveable {
        mutableIntStateOf(-1)
    }
    val data:List<List<Any>> = usersAndMatches.map {
        listOf(it.key,it.value)
    }
    if(showDialog.value) {
        SelectPlayerDialog(showDialog,data,index.intValue, sendInvite = {
            viewModel.sendInvite(data[index.intValue][0].toString())
        })
    }
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp
                )
        ) {
            Text(
                text = "User",
                modifier = modifier
                    .weight(2f)
                    .background(DeepOrange50200),
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
                text = "Matches",
                modifier = modifier
                    .weight(1f)
                    .background(
                        Brown50600
                    ),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(
                        R.font.kalam_bold,
                        FontWeight.Bold
                    )
                ),
                color = DeepOrange50200,
                fontSize = TextUnit(25f, TextUnitType.Sp)
            )
        }
        val list: List<List<Any>> = usersAndMatches.map {
            listOf(it.key, it.value)
        }
        LazyColumn(
            modifier = modifier.fillMaxWidth()
        ) {
            items(list.size) { it ->
                Row(
                    modifier=modifier.clickable {
                        showDialog.value=true
                        index.intValue=it
                    }
                ) {
                    Text(
                        text = list[it][0].toString(),
                        modifier = modifier
                            .weight(2f)
                            .background(DeepOrange50),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = Brown50600,
                        fontSize = TextUnit(23f, TextUnitType.Sp)
                    )
                    Text(
                        text = list[it][1].toString(),
                        modifier = modifier
                            .weight(1f)
                            .background(
                                Pink80
                            ),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(
                                R.font.kalam_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = PurpleGrey40,
                        fontSize = TextUnit(23f, TextUnitType.Sp)
                    )
                }

            }
        }
    }
}

@Composable
fun SelectPlayerDialog(
    showDialog: MutableState<Boolean>,
    list:List<List<Any>>, index:Int,
    sendInvite:()->Unit
) {
    val showTimer= rememberSaveable {
        mutableStateOf(false)
    }
    if(showTimer.value) {
        TimerDialog(
            showTimer,
            showDialog
        )
    }
        AlertDialog(
            dismissButton={
                TextButton(onClick = {
                    //Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
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
                showDialog.value=false
            },
            confirmButton = {
                TextButton(onClick = {
                   // Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
                    sendInvite()
                    showTimer.value=true
//                    showDialog.value=false
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
                    text = "Send Invite",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    fontSize = TextUnit(25f, type = TextUnitType.Sp),
                    color = Color.DarkGray
                )
            },
            text = {
                Text(
                    text = "Do you want to send invite to ${list[index][0]}?",
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
fun TimerDialog(
    showTimer:MutableState<Boolean> = mutableStateOf(true),
    showDialog: MutableState<Boolean>
) {
    DisposableEffect(key1 = true) {
        onDispose {
            showDialog.value=false
        }
    }
    val timer= rememberSaveable {
        mutableStateOf(10)
    }
    LaunchedEffect(true) {
        while (timer.value>0) {
            timer.value-=1
            delay(1000)
        }
        if(timer.value==0) {
            showTimer.value=false
        }
    }
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
                    )){
                        append("${timer.value} s")
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
fun InvitedDialog(
    inviteSender:String="",
    declineInvite:()->Unit,
    acceptInvite:()->Unit
) {
    val time= rememberSaveable {
        mutableStateOf(10)
    }
    val context=LocalContext.current
    LaunchedEffect(key1 = true) {
        while(time.value>0) {
            time.value-=1
            delay(1000)
        }
        if(time.value==0) {
            Toast.makeText(context,"Invitation Declined due to no response",Toast.LENGTH_SHORT).show()
            declineInvite()
        }
    }
    AlertDialog(
        dismissButton={
            TextButton(onClick = {
                Toast.makeText(context,"Declined Invite",Toast.LENGTH_SHORT).show()
                declineInvite()
            }) {
                Text(
                    text = "No",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        onDismissRequest = {
            declineInvite()
        },
        confirmButton = {
            TextButton(onClick = {
                Toast.makeText(context,"Invite Accepted!",Toast.LENGTH_SHORT).show()
                acceptInvite()
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
                text = "Accept or Reject?",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(25f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        text = {
            Text(
                text = "Do you want to play game with $inviteSender",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        containerColor = DeepOrange50300
    )
}

