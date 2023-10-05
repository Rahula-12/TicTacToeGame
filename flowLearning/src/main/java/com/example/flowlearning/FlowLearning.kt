package com.example.flowlearning
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

val channel= Channel<Int>()
fun main() {
//    producer()
//    consumer()
    val data= producer()
    runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            data.collect{
                delay(500)
                println("Second $it")
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            data.collect{
                delay(500)
                println("Third $it")
            }
        }
        data.collect{
            delay(1000)
            println("First $it")
        }
    }
}

//fun producer() {
//    CoroutineScope(Dispatchers.IO).launch {
//        channel.send(1)
//        delay(1000)
//        channel.send(2)
//        delay(2000)
//        channel.send(3)
//    }
//}
//
//fun consumer() {
//    runBlocking {
//        println(channel.receive().toString())
//        println(channel.receive().toString())
//        println(channel.receive().toString())
//    }
//}

fun producer()= flow {
    for(i in 1..5) {
        emit(i)
    }
}
class FlowLearning {
}