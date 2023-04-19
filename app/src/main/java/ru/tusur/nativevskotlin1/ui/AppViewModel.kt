package ru.tusur.nativevskotlin1.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tusur.nativevskotlin1.*
import ru.tusur.nativevskotlin1.model.Mandelbrot
import ru.tusur.nativevskotlin1.model.UiState
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class AppViewModel( application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()



    private var points: Pair<Int,ArrayList<Double>>?=null
    private var pointsCpp: Pair<Int,List<Double>>?=null
    private var lastKotlinCalcTimer:Long=0


    // Calculate Mandelbrot with Kotlin
    //-------------------------------------------

    fun launchKotlinCalc(){
        viewModelScope.launch {
            _uiState.update { it.copy(kotlinCalcInProgress=true) }
           val timerKotlinCalc=measureTimeMillis {
               withContext(Dispatchers.Default) {
                    processKotlinCalc();
                }
            }
            _uiState.update { it.copy(kotlinCalcInProgress=false,kotlinCalcFinished=true, kotlinCalcTime = timerKotlinCalc, kotlinPointsFound = points?.first ?:0) }
        }
    }

    private suspend fun processKotlinCalc(){
        val mandelbrot= Mandelbrot()
        points = mandelbrot.calc(SCR_WIDTH, SCR_HEIGHT, xStart, xEnd, yStart, yEnd, MAX_ITER)
        //buildPNG(points, context= this.getApplication())
    }

    // Calculate Mandelbrot with C++
    //-------------------------------------------

    fun launchNativeCalc(){
        viewModelScope.launch {
            _uiState.update { it.copy(nativeCalcInProgress =true) }
            val timeCPPCalc= measureTimeMillis {
                withContext(Dispatchers.Default) {
                    processNativeCalc()
                }
            }
            _uiState.update { it.copy(nativeCalcInProgress=false,nativeCalcFinished=true, nativeCalcTime = timeCPPCalc, nativePointsFound = pointsCpp?.first ?: 0)  }
        }
    }


    private external fun arrFromJNI(): DoubleArray

    private suspend fun processNativeCalc(){
        val arr=arrFromJNI()
        val timeConversion= measureTimeMillis {
            val arrList= arr.asList()
            pointsCpp=Pair(arr.size,arrList)
        }
        Log.d("Conversion time","time = $timeConversion")
    }



}