package com.example.kotlinoops

class Car{
    var colour:String="Blue"
    var model:String="Hyundai"

    fun describe() {
        println("Car's \"colour\" is $colour and \"model\" is $model")
    }
}

fun main() {
    val car=Car()
    car.describe()
    car.colour="Black"
    car.model="BMW"
    car.describe()
}