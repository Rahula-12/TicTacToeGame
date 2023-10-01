package com.example.flowlearning

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.util.concurrent.Flow

fun main() {
    runBlocking {
        try {
            val data = producer4()
                .map {
                    delay(1000)
                    2*it
                    println("Map Thread:- ${Thread.currentThread().name}")
                }
                .flowOn(Dispatchers.Default)
            data.collect {
                println("Receiver Thread:- ${Thread.currentThread().name}")
            }
        }
        catch (e:Exception){
            println(e.message)
        }
    }
}
fun producer4() = flow<Int>{
    for(i in 1..5) {
        delay(1000)
        emit(i)
        println("Emitter Thread:- ${Thread.currentThread().name}")
        if(i==3)
        throw Exception("Random exception")
    }
}.catch {
    println("Emitter exception handled ${it.message}")
}