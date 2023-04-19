package ru.tusur.nativevskotlin1.model

const  val HD_WIDTH=1920
const  val HD_HEIGHT=1280


class Mandelbrot {

    // подготовить vector так как зранее неизвестно количество точек для закрашивания
    private var points: ArrayList<Double> = ArrayList(300000);

    public fun calc(
        width : Int,
        height : Int,
        startX : Double,
        endX : Double,
        startY : Double,
        endY : Double,
        maxIter : Int
    ):Pair<Int,ArrayList<Double>>{
        points.clear()
        val stepX:Double
        val stepY:Double
        if (height>HD_HEIGHT || width>HD_WIDTH){
            stepX=(endX-startX)/width;
            stepY=(endY-startY)/height;
        }
        else{
            stepX= (endX-startX)/HD_WIDTH
            stepY=(endY-startY)/HD_HEIGHT
        }
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
                }
                if (sqrAbs(cn)<=4){
                    points.add(x);
                    points.add(y);
                }
                y+=stepY
            }
            x+=stepX
        }

       // Log.d("Mandelbrot","Found ${points.size/2} points")
        return Pair(points.size,points)
    }


}