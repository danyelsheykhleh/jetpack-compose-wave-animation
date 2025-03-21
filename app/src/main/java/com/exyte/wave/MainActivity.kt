package com.exyte.wave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.exyte.wave.animating.WaterLevelState
import com.exyte.wave.ui.theme.WaveTheme
import com.exyte.wave.waterdrops.WaterDropLayout
import com.exyte.wave.waterdrops.wave.WaveParams

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WaveTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    ) {


                        Surface(
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                        ) {
                            val screenWidth = LocalConfiguration.current.screenWidthDp
                            val points = remember { screenWidth / waveGap }
                            var waterLevelState by remember { mutableStateOf(WaterLevelState.Animating) }
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
                                )
                            )
                        }

                        Image(
                            modifier = Modifier.size(200.dp),
                            painter = painterResource(R.drawable.emptybowloverlayelement),
                            contentDescription = null
                        )
//                        Icon(
//                            painter = painterResource(R.drawable.ellipse_764),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                .fillMaxWidth()
//                                .align(Alignment.BottomCenter), tint = Color.Black.copy(alpha = 0.2f)
//                        )
//
//                        Image(
//                            painter = painterResource(R.drawable.ellipse_765),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                .fillMaxWidth()
//                                .background(Color.Red)
//                                .align(Alignment.CenterStart),
//                        )


                    }

                }

            }
        }
    }
}

const val waveGap = 20