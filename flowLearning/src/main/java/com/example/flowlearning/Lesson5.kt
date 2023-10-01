package com.example.flowlearning

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("Hi")
    runBlocking(Dispatchers.IO) {
        val data=producer5()
        CoroutineScope(Dispatchers.IO).launch{
            delay(2500)
            data.collect {
                println("Second User:$it")
            }
        }
        data.collect {
            println("First User:$it")
        }
    }
}

fun producer5(): SharedFlow<Int> {
    val mutableSharedFlow= MutableSharedFlow<Int>(replay = 1)
    CoroutineScope(Dispatchers.IO).launch {
        for(i in 1..5) {
            delay(1000)
            mutableSharedFlow.emit(i)
        }
    }
    return mutableSharedFlow
}