package com.example.flowlearning

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.Exception

fun main(){
    val data= producer2()
    runBlocking {
//        val job=GlobalScope.launch {
            println(Thread.currentThread().name)
           val job= GlobalScope.launch {
                data.collect {
                    println("first $it")
                }
            }
        GlobalScope.launch {
            data.collect {
                println("third $it")
            }
        }
//            delay(2500)
//            job.cancel()
        delay(2000)
        data.collect {
            println("second $it")
        }
    }
}
fun producer2()= flow {
    for(i in 1..5){
        delay(1000)
        emit(i)
    }
}