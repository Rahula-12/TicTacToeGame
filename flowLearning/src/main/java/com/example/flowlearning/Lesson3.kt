package com.example.flowlearning

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Flow
import kotlin.system.measureTimeMillis

fun main() {
    val data=producer3()
    runBlocking {
//        data
//            .onStart {
//            emit(0)
//            println("Started")
//        }
//            .onCompletion {
//                emit(6)
//                println("Completed")
//            }
//            .onEach {
//                println("$it is about to be emitted.")
//            }
//            .collect {
//                println(it)
//            }
        val time= measureTimeMillis {
            data
//                .map { it.toString() }
//                .onStart {
//                    emit("0")
//                    println("Started")
//                }
//                .onCompletion {
//                    emit("20")
//                    println("Completed")
//                }
//                .onEach {
//                    println("$it is about to be emitted.")
//                }
                .buffer(5)
                .collect {
                    delay(1500)
                    println(it)
                }
        }
        println(time)
    }
}

fun producer3()= flow {
        for(i in 1..5) {
            delay(1000)
            emit(i)
        }
}