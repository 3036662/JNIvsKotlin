package ru.tusur.nativevskotlin1.model

import android.util.Log

const  val HD_WIDTH=1920
const  val HD_HEIGHT=1280


class Mandelbrot {

    // подготовить vector так как зранее неизвестно количество точек для закрашивания
    private var points: ArrayList<Double> = ArrayList(300000);

     fun calc(
        startX : Double,
        endX : Double,
        startY : Double,
        endY : Double,
        maxIter : Int
    ):List<Double>{
        points.clear()

        var iterNumb:Int=0; //total iterations
        val stepX:Double
        val stepY:Double

        stepX= (endX-startX)/HD_WIDTH
        stepY=(endY-startY)/HD_HEIGHT
        // пройти все точки с нужным шагом
        var x=startX
        while(x<=endX){
            var y=startY
            while (y<=endY){
                var cn=Complex() //New complex 0
                var i:Int=0
                while (i<maxIter && sqrAbs(cn)<4){
                        cn = square(cn)+Complex(x,y)
                        ++i
                    ++iterNumb
                }
                if (sqrAbs(cn)<=4){
                    points.add(x);
                    points.add(y);
                }
                y+=stepY
            }
            x+=stepX
        }

        Log.d("Native VS Kotlin","Kotlin iterations number =$iterNumb")
        //return Pair(points.size,points)
      return points
    }


}