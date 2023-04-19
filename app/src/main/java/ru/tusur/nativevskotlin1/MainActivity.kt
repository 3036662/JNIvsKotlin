package ru.tusur.nativevskotlin1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import kotlinx.coroutines.Dispatchers


import kotlinx.coroutines.withContext
import ru.tusur.nativevskotlin1.ui.theme.AppScreen
import ru.tusur.nativevskotlin1.ui.theme.NativeVsKotlin1Theme
import kotlin.system.measureTimeMillis

const val xStart:Double=-2.0
const val xEnd:Double=1.0;
const val yStart:Double=-1.0;
const val yEnd:Double=1.0;
const val SCR_WIDTH:Int = 800;
const val SCR_HEIGHT:Int = 600;
const val MAX_ITER:Int=200




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            NativeVsKotlin1Theme{
                Surface {
                    AppScreen()
                }
                
            }
        }


    }

    companion object {
        // Used to load the 'nativevskotlin1' library on application startup.
        init {
            System.loadLibrary("nativevskotlin1")
        }
    }

}