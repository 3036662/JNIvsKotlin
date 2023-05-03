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
import ru.tusur.nativevskotlin1.model.Mandelbrot
import ru.tusur.nativevskotlin1.model.UiState
import ru.tusur.nativevskotlin1.model2.MandelbrotOptimized
import ru.tusur.nativevskotlin1.png.buildPNG
import kotlin.system.measureTimeMillis

const val xStart:Double=-2.0
const val xEnd:Double=1.0
const val yStart:Double=-1.0
const val yEnd:Double=1.0
const val MAX_ITER:Int=200


const val KOTLINPNG:String= "kotlinMandelbrot.png"
const val KOTLIN2PNG:String= "kotlin2Mandelbrot.png"
const val  CPPPNG:String ="cppMandelbrot.png"
const val  CPPPNG2:String ="cppMandelbrot2.png"
class AppViewModel( application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var points: List<Double>?=null
    private var pointsPrimitiveTypes: List<Double>?=null
    private var pointsCpp: List<Double>?=null
    private var pointsCpp2: List<Double>?=null


    // Calculate Mandelbrot with Kotlin
    //-------------------------------------------

     fun launchKotlinCalc(){

        viewModelScope.launch {//(Dispatchers.Main) could be used instead of withContext
            _uiState.update { it.copy(kotlinCalcInProgress=true) }
           val timerKotlinCalc=measureTimeMillis {
               withContext(Dispatchers.Default) {
                    processKotlinCalc()
               }
            }
            _uiState.update { it.copy(kotlinCalcInProgress=false,kotlinCalcFinished=true, kotlinCalcTime = timerKotlinCalc, kotlinPointsFound = points?.size ?:0) }
        }
    }

    private  fun processKotlinCalc(){
        val mandelbrot= Mandelbrot()
        points = mandelbrot.calc( xStart, xEnd, yStart, yEnd, MAX_ITER)
    }

    // Calculate Mandelbrot with Kotlin
    //-------------------------------------------

    fun launchKotlinCalcOptimized(){

        viewModelScope.launch {//(Dispatchers.Main) could be used instead of withContext
            _uiState.update { it.copy(kotlinOptimizedCalcInProgress=true) }
            val timerKotlinCalc=measureTimeMillis {
                withContext(Dispatchers.Default) {
                    processKotlinCalcOptimized()
                }
            }
            _uiState.update { it.copy(kotlinOptimizedCalcInProgress=false,kotlinOptimizedCalcFinished=true,
                kotlinOptimizedCalcTime = timerKotlinCalc, kotlinOptimizedPointsFound = pointsPrimitiveTypes?.size ?:0) }
        }
    }

    private fun processKotlinCalcOptimized(){
        val mandelbrot= MandelbrotOptimized()
        pointsPrimitiveTypes = mandelbrot.calc( xStart, xEnd, yStart, yEnd, MAX_ITER)
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
            _uiState.update { it.copy(nativeCalcInProgress=false,nativeCalcFinished=true, nativeCalcTime = timeCPPCalc, nativePointsFound = pointsCpp?.size ?: 0)  }
        }
    }
    //C++ calc optimized
    fun launchNativeCalcOptimized(){
        viewModelScope.launch {
            _uiState.update { it.copy(cppOptimizedCalcInProgress =true) }
            val timeCPPCalc= measureTimeMillis {
                withContext(Dispatchers.Default) {
                    processNativeOptimizedCalc()
                }
            }
            _uiState.update { it.copy(cppOptimizedCalcInProgress=false, cppOptimizedCalcFinished = true, cppOptimizedTime = timeCPPCalc, cppOptimizedPointsFound = pointsCpp2?.size ?: 0)  }
        }
    }


    private external fun arrFromJNI(): DoubleArray
    private external fun arrFromJNI2(): DoubleArray //no complex

    private  fun processNativeCalc(){
        val arr=arrFromJNI()
        val timeConversion= measureTimeMillis {
            val arrList= arr.asList()
            pointsCpp=arrList
        }
        Log.d("Conversion time","time = $timeConversion")
    }

    //Native Optimized
    private  fun processNativeOptimizedCalc(){
        val arr=arrFromJNI2()
        val timeConversion= measureTimeMillis {
            val arrList= arr.asList()
            pointsCpp2=arrList
        }
        Log.d("Conversion time","time = $timeConversion")
    }



    // Create Pngs
    //----------------------------
    fun createPngs(){
        viewModelScope.launch {
            _uiState.update { it.copy(pngInProgress = true) }
            withContext(Dispatchers.Default){
                if (points!=null && pointsCpp!=null && pointsPrimitiveTypes!=null && pointsCpp2!=null) {
                    buildPNG(points!!, getApplication(), KOTLINPNG)
                    buildPNG(pointsPrimitiveTypes!!,getApplication(), KOTLIN2PNG)
                    buildPNG(pointsCpp!!,getApplication(), CPPPNG)
                    buildPNG(pointsCpp2!!,getApplication(), CPPPNG2)
                }
            }
            _uiState.update { it.copy(pngInProgress = false, pngReady = true) }
        }
    }

}