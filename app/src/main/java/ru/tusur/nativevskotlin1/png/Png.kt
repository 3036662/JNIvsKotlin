package ru.tusur.nativevskotlin1.png

import android.content.Context
import android.util.Log
import ar.com.hjg.pngj.ImageInfo
import ar.com.hjg.pngj.ImageLineHelper
import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngWriter
import ru.tusur.nativevskotlin1.model.HD_HEIGHT
import ru.tusur.nativevskotlin1.model.HD_WIDTH
import ru.tusur.nativevskotlin1.xEnd
import ru.tusur.nativevskotlin1.xStart
import ru.tusur.nativevskotlin1.yEnd
import ru.tusur.nativevskotlin1.yStart





fun buildPNG(points: List<Double>,context:Context,filename:String) {
       // val outputStream = ByteArrayOutputStream() // output stream
       // val file = File(context.filesDir, filename)
       val outputStream=context.openFileOutput(filename, Context.MODE_PRIVATE)

        val imageInfo = ImageInfo(HD_HEIGHT, HD_WIDTH, 8, false) // 90 degree rotate
        val pngWriter = PngWriter(outputStream, imageInfo)
        val iline: ImageLineInt = ImageLineInt(imageInfo)


        val stepX = (xEnd - xStart) / HD_WIDTH
        val stepY = (yEnd - yStart) / HD_HEIGHT

        var x = xStart
        var i = 0
        var col = 0
    var row=0
        while (x < xEnd) {
            var y = yStart
            while (y <= yEnd) {
                var r = 255
                var g = 255
                val b = 255  // rgb
                if (points[i] == x && points[i + 1] == y) { // blue a point
                    if (i+2<points.size){i += 2}
                    r = 0;g = 0
                }
                ImageLineHelper.setPixelRGB8(iline, col, r, g, b)
                ++col
                y += stepY
            }
            //write Line
           //s Log.d(" ru.tusur.nativevskotlin1 ","${iline.size} col=$col row=$row")
            if (row<imageInfo.rows && col<=imageInfo.cols) {
                pngWriter.writeRow(iline)
            }
            row++
            col=0
            x += stepX
        }
        pngWriter.end()
        Log.d("storage",context.filesDir.toString())

}
