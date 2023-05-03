package ru.tusur.nativevskotlin1.model2

import android.util.Log
import kotlin.collections.ArrayList

const val HD_WIDTH = 1920
const val HD_HEIGHT = 1280

class MandelbrotOptimized {

    private var points: ArrayList<Double> = ArrayList()

    fun calc(
        startX: Double,
        endX: Double,
        startY: Double,
        endY: Double,
        maxIter: Int
    ): List<Double> {
        points.clear()
        var iterNumb = 0 //total iterations


        // Изменить создание переменных step на вычисление внутри цикла for
        val width = HD_WIDTH
        val height = HD_HEIGHT
        val stepX = (endX - startX) / width
        val stepY = (endY - startY) / height
        val c = Complex()
        var x = startX
        while (x <= endX) {
            var y = startY
            while (y <= endY) {
                c.real = 0.0
                c.im = 0.0
                var i = 0
                while (i < maxIter && c.sqrAbs() < 4) {
                    c.squareSelf()
                    c.real += x
                    c.im += y
                    ++i
                    ++iterNumb
                }
                if (c.sqrAbs() <= 4) {
                    points.add(x)
                    points.add(y)
                }
                y += stepY
            }
            x += stepX
        }

        Log.d("Native VS Kotlin", "Kotlin iterations number =$iterNumb")
        Log.d("nativevskotlin1","Kotlin Complex objetcs created(optimized) =${Complex.counter}")
        return points
    }

}