package com.example.flowlearning

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
            val data=producer6()
//        CoroutineScope(Dispatchers.IO).launch{
//            delay(3000)
//            println("First ${data.value}")
//        }
//        CoroutineScope(Dispatchers.IO).launch{
//            delay(6000)
            println("Second ${data.value}")
            delay(3000)
            println("Second ${data.value}")
            delay(2000)
            println("Second ${data.value}")
//        }

//        data.collect{
//            println(it)
//        }
    }
}

fun producer6():StateFlow<Int> {
    val mutableStateFlow= MutableStateFlow(0)
    CoroutineScope(Dispatchers.IO).launch{
        for (i in 1..5) {
            delay(1000)
            mutableStateFlow.emit(i)
        }
    }
    return mutableStateFlow
}