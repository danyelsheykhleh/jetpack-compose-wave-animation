package com.exyte.wave

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.wave.animating.WaterLevelState
import com.exyte.wave.waterdrops.CircularWaveView
import com.exyte.wave.waterdrops.wave.WaterDropText
import com.exyte.wave.waterdrops.wave.WaveParams

@Composable
fun CircularWaveScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val points = remember { screenWidth / waveGap }
    var waterLevelState by remember { mutableStateOf(WaterLevelState.StartReady) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Circular Wave Progress",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        CircularWaveView(
            size = 250,
            waterLevelState = waterLevelState,
            onWavesClick = {
                waterLevelState = if (waterLevelState == WaterLevelState.Animating) {
                    WaterLevelState.StartReady
                } else {
                    WaterLevelState.Animating
                }
            }
        ) {
            WaterDropText(
                modifier = Modifier,
                align = Alignment.Center,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                ),
                waveParams = WaveParams(
                    pointsQuantity = points,
                    maxWaveHeight = 20f
                )
            )
        }
        
        Text(
            text = "Tap to toggle animation",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}