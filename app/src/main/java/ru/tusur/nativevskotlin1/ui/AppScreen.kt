package ru.tusur.nativevskotlin1.ui.theme


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import ru.tusur.nativevskotlin1.R
import ru.tusur.nativevskotlin1.ui.AppViewModel
import ru.tusur.nativevskotlin1.ui.LoadingAnimation2


@Composable
fun AppScreen(modifier: Modifier=Modifier){
    val viewModel: AppViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (!uiState.kotlinCalcInProgress) {
            Button(modifier = modifier.padding(20.dp), onClick = { viewModel.launchKotlinCalc() }) {
                Text(stringResource(R.string.launch_kotlinButton))
            }
        }
        if (uiState.kotlinCalcInProgress){
            LoadingAnimation2()
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(id = R.string.kotlin_in_progress), fontWeight = FontWeight.Bold)
        }
        if (uiState.kotlinCalcFinished && !uiState.kotlinCalcInProgress){
            Text(stringResource(R.string.calc_finished));
            Text("Time :${uiState.kotlinCalcTime} ms")
            Text("${uiState.kotlinPointsFound} points found")
        }

        // native

        if (!uiState.nativeCalcInProgress) {
            Button(modifier = modifier.padding(20.dp), onClick = { viewModel.launchNativeCalc() }) {
                Text(stringResource(R.string.calculate_with_cpp))
            }
        }

        if (uiState.nativeCalcInProgress){
            LoadingAnimation2()
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(R.string.native_is_calculating), fontWeight = FontWeight.Bold)
        }

        if (uiState.nativeCalcFinished && !uiState.nativeCalcInProgress){
            Text("C++ Mandelbrot calculation is finished");
            Text("${uiState.nativePointsFound} points found")
            Text("Time :${uiState.nativeCalcTime} ms")
        }


    }

}
