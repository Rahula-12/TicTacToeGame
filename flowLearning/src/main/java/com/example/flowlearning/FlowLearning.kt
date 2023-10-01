package com.example.flowlearning
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

val channel= Channel<Int>()
fun main() {
    producer()
    consumer()
}

fun producer() {
    CoroutineScope(Dispatchers.IO).launch {
        channel.send(1)
        delay(1000)
        channel.send(2)
        delay(2000)
        channel.send(3)
    }
}

fun consumer() {
    runBlocking {
        println(channel.receive().toString())
        println(channel.receive().toString())
        println(channel.receive().toString())
    }
}
class FlowLearning {
}