package ru.tusur.nativevskotlin1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface

import ru.tusur.nativevskotlin1.ui.AppScreen
import ru.tusur.nativevskotlin1.ui.theme.NativeVsKotlin1Theme


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