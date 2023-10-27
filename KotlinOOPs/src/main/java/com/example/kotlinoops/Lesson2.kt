package com.example.kotlinoops

open class Car2
    (var color:String="Blue",var model:String="BMW")
{
    fun describe() {
        println("Car's \"colour\" is $color and \"model\" is $model")
    }
}
//open class A(open val number: Int) {
//    open fun foo() = number
//}
//
//class B(number: Int): A(number) {
//    override val number: Int=2*super.number
//
//    fun showDiff() {
//        println("$number ${super.number}")
//    }
//}
fun main() {
    val car=Car2()
    car.describe()
}