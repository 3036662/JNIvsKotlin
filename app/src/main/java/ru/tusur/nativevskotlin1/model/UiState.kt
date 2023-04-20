package ru.tusur.nativevskotlin1.model

data class UiState(
    val kotlinCalcInProgress: Boolean =false,
    val kotlinCalcFinished:Boolean=false,
    val nativeCalcInProgress: Boolean =false,
    val nativeCalcFinished:Boolean=false,
    val kotlinCalcTime:Long=0,
    val nativeCalcTime:Long=0,
    val kotlinPointsFound:Int=0,
    val nativePointsFound:Int=0,
    val pngInProgress:Boolean=false,
    val pngReady:Boolean=false

)
