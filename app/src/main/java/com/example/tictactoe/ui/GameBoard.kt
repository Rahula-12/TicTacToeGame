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
fun GameBoard(
    modifier:Modifier=Modifier,
    direction:Int=-1
){
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
                when(direction){
                  1->drawLine(
                      color= Color.Red,
                      strokeWidth=20f,
                      start = Offset(0f,size.height/6),
                      end= Offset(size.width,size.height/6)
                  )
                    2->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start = Offset(0f,size.height/2),
                        end= Offset(size.width,size.height/2)
                    )
                    3->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start = Offset(0f,size.height*5/6),
                        end= Offset(size.width,size.height*5/6)
                    )
                    4->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start = Offset(size.width/6,0f),
                        end= Offset(size.width/6,size.height)
                    )
                    5->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start = Offset(size.width/2,0f),
                        end= Offset(size.width/2,size.height)
                    )
                    6->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start = Offset(size.width*5/6,0f),
                        end= Offset(size.width*5/6,size.height)
                    )
                    7->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start=Offset(0f,0f),
                        end=Offset(size.width,size.height)
                    )
                    8->drawLine(
                        color= Color.Red,
                        strokeWidth=20f,
                        start=Offset(0f,size.height),
                        end=Offset(size.width,0f)
                    )
                }
            }
        )
    }

@Preview
@Composable
fun Previews(){
    GameBoard()
}