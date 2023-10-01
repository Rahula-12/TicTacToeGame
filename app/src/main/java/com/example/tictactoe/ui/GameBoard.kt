package com.example.tictactoe.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameBoard(modifier:Modifier=Modifier){
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Transparent)
            ,
            onDraw = {
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth=30f,
                    start = Offset(size.width*1/3,0f),
                    end = Offset(size.width*1/3,size.height)
                )
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth=30f,
                    start = Offset(size.width*2/3,0f),
                    end = Offset(size.width*2/3,size.height)
                )
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth=30f,
                    start = Offset(0f,(size.height*1)/3),
                    end = Offset(size.width,(size.height*1)/3)
                )
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth=30f,
                    start = Offset(0f,(size.height*2)/3),
                    end = Offset(size.width,(size.height*2)/3)
                )
            }
        )
    }

@Composable
fun Cross(modifier: Modifier=Modifier){
        Canvas(
            modifier = modifier
                .size(100.dp)
                .background(Color.Transparent),
            onDraw = {
                drawLine(
                    color=Color.Red,
                    start= Offset(0f,0f),
                    end= Offset(size.width,size.height),
                    strokeWidth= 20f
                )
                drawLine(
                    color=Color.Red,
                    start= Offset(size.width,0f),
                    end= Offset(0f,size.height),
                    strokeWidth= 20f
                )
            }
        )
}

@Composable
fun Circle(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier
            .size(100.dp)
            .background(Color.Transparent)
            .padding(5.dp)
        ,
        onDraw = {
            drawCircle(
                color=Color.Red,
                style= Stroke(width=20f)
            )
        }
    )
}

@Composable
fun WinHorizontalLine1(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(0f,size.height/6),
                end= Offset(size.width,size.height/6)
            )
        })
}

@Composable
fun WinHorizontalLine2(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(0f,size.height/2),
                end= Offset(size.width,size.height/2)
            )
        })
}

@Composable
fun WinHorizontalLine3(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(0f,size.height*5/6),
                end= Offset(size.width,size.height*5/6)
            )
        })
}

@Composable
fun WinVerticalLine1(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(size.width/6,0f),
                end= Offset(size.width/6,size.height)
            )
        })
}

@Composable
fun WinVerticalLine2(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(size.width/2,0f),
                end= Offset(size.width/2,size.height)
            )
        })
}

@Composable
fun WinVerticalLine3(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start = Offset(size.width*5/6,0f),
                end= Offset(size.width*5/6,size.height)
            )
        })
}

@Composable
fun DiagnolLine1(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start=Offset(0f,0f),
                end=Offset(size.width,size.height)
            )
        })
}

@Composable
fun DiagnolLine2(modifier: Modifier=Modifier){
    Canvas(
        modifier = modifier.size(300.dp),
        onDraw = {
            drawLine(
                color= Color.Red,
                start=Offset(0f,size.height),
                end=Offset(size.width,0f)
            )
        })
}

@Preview
@Composable
fun Previews(){
//    Column {
//        Cross()
//        Circle()
//    }
    WinHorizontalLine1()
    WinHorizontalLine2()
    WinHorizontalLine3()
    WinVerticalLine1()
    WinVerticalLine2()
    WinVerticalLine3()
    DiagnolLine1()
    DiagnolLine2()
}