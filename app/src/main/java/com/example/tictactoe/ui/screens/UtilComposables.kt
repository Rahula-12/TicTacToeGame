package com.example.tictactoe.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.DeepOrange50300

@Composable
fun NoInternetDialog(
    dismissDialog:()->Unit
) {
    AlertDialog(
        dismissButton={
            TextButton(onClick = {
                //Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
            }) {

            }
        },
        onDismissRequest = {
            dismissDialog()
        },
        confirmButton = {
            TextButton(onClick = {
                // Toast.makeText(context,"Wait for Confirmation",Toast.LENGTH_SHORT).show()
                dismissDialog()
//                    showDialog.value=false
            }) {
                Text(
                    text = "Ok",
                    fontFamily = FontFamily(Font(R.font.kalam_bold)),
                    color= Color.DarkGray
                )
            }
        },
        title={
            Text(
                text = "Internet Connectivity",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(25f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        text = {
            Text(
                text = "Please check your internet connection.",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.kalam_bold)),
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                color = Color.DarkGray
            )
        },
        containerColor = DeepOrange50300
    )
}