package ru.tusur.nativevskotlin1.ui




import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

import ru.tusur.nativevskotlin1.R
import java.io.File


@Composable
fun AppScreen(modifier: Modifier=Modifier){
    val viewModel: AppViewModel = viewModel()
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    var sliderPosition by remember { mutableStateOf(0f) }


    Column(
            modifier
                .fillMaxWidth()//
                .fillMaxHeight().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        Text(text = "JVM Machine version = ${System.getProperty("java.vm.version")}")
        Text(text= "JAVA Home dir = ${System.getProperty("java.home")}")
    // Show two button Calculate
    //--------------------------------------------------
            if (!uiState.kotlinCalcInProgress && !uiState.pngInProgress) {
                Button(
                    modifier = modifier.padding(20.dp),
                    onClick = { viewModel.launchKotlinCalc() }) {
                    Text(stringResource(R.string.launch_kotlinButton))
                }
            }
            if (uiState.kotlinCalcInProgress) {
                LoadingAnimation2()
                Spacer(modifier = Modifier.height(20.dp))
                Text(stringResource(id = R.string.kotlin_in_progress), fontWeight = FontWeight.Bold)
            }
            if (uiState.kotlinCalcFinished && !uiState.kotlinCalcInProgress) {
                Text(stringResource(R.string.calc_finished))
                Text("Time :${uiState.kotlinCalcTime} ms")
                Text("${uiState.kotlinPointsFound} points found")
            }
    //--------------------------------------------------
    // Calculation process animation

            if (!uiState.nativeCalcInProgress && !uiState.pngInProgress) {
                Button(
                    modifier = modifier.padding(20.dp),
                    onClick = { viewModel.launchNativeCalc() }) {
                    Text(stringResource(R.string.calculate_with_cpp))
                }
            }

            if (uiState.nativeCalcInProgress) {
                LoadingAnimation2()
                Spacer(modifier = Modifier.height(20.dp))
                Text(stringResource(R.string.native_is_calculating), fontWeight = FontWeight.Bold)
            }

            if (uiState.nativeCalcFinished && !uiState.nativeCalcInProgress) {
                Text("C++ Mandelbrot calculation is finished")
                Text("${uiState.nativePointsFound} points found")
                Text("Time :${uiState.nativeCalcTime} ms")
            }

    //--------------------------------------------------
    // create Png button

            if (uiState.nativeCalcFinished && uiState.kotlinCalcFinished) {
                Button(modifier = modifier.padding(20.dp), onClick = { viewModel.createPngs() }) {
                    Text(stringResource(R.string.create_png))
                }
            }
            if (uiState.pngInProgress) {
                LoadingAnimation2()
                Text("Creating png files...")
            }

            // show generated Images

            if (uiState.pngReady &&
                File("${LocalContext.current.filesDir}/$KOTLINPNG").exists() &&
                File("${LocalContext.current.filesDir}/$CPPPNG").exists()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.height(200.dp)) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${LocalContext.current.filesDir}/$KOTLINPNG")
                            .build(),
                        contentDescription = "icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${LocalContext.current.filesDir}/$CPPPNG")
                            .build(),
                        contentDescription = "icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
    }
}
