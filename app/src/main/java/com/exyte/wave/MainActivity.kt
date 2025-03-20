package com.exyte.wave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.exyte.wave.animating.WaterLevelState
import com.exyte.wave.ui.theme.WaveTheme
import com.exyte.wave.waterdrops.WaterDropLayout
import com.exyte.wave.waterdrops.wave.WaterDropText
import com.exyte.wave.waterdrops.wave.WaveParams

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WaveTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                    ) {
                        val screenWidth = LocalConfiguration.current.screenWidthDp
                        val points = remember { screenWidth / waveGap }
                        var waterLevelState by remember { mutableStateOf(WaterLevelState.StartReady) }
                        WaterDropLayout(
                            modifier = Modifier,
                            waveDurationInMills = 2500,
                            waterLevelState = waterLevelState,
                            onWavesClick = {
                                waterLevelState =
                                    if (waterLevelState == WaterLevelState.Animating) {
                                        WaterLevelState.StartReady
                                    } else {
                                        WaterLevelState.Animating
                                    }
                            }, waveParams = WaveParams(
                                pointsQuantity = points,
                                maxWaveHeight = 30f
                            ))
                    }
                }
            }
        }
    }
}

const val waveGap = 20