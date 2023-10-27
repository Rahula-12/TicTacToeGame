package com.example.kotlinoops

fun main() {
    Repository.startFetch()
    getResult(Repository.getState())
    Repository.errorFetch()
    getResult(Repository.getState())
    Repository.finishedFetch()
    getResult(Repository.getState())
}

fun getResult(result: Result) = when(result){
    Result.SUCCESS-> println("Success!")
    Result.FAILURE->println("Failure!")
    Result.ERROR->println("Error!")
    Result.IDLE->println("Idle!")
    Result.LOADING->println("Loading")
}

enum class Result{
    SUCCESS,
    ERROR,
    FAILURE,
    IDLE,
    LOADING
}

object Repository{
    private var loadState:Result=Result.IDLE
    private var dataFetched:String?=null
    fun startFetch() {
        loadState=Result.LOADING
        dataFetched="data"
    }

    fun finishedFetch(){
        loadState=Result.SUCCESS
    }

    fun errorFetch() {
        loadState=Result.ERROR
    }
    fun getState():Result{
        return loadState
    }
}