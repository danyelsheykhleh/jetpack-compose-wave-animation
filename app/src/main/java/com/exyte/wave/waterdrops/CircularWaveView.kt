package com.exyte.wave.waterdrops

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.exyte.wave.animating.WaterLevelState
import com.exyte.wave.waterdrops.wave.WaterDropText

@Composable
fun CircularWaveView(
    modifier: Modifier = Modifier,
    size: Int = 200,
    waveDurationInMills: Long = 10000L,
    waterLevelState: WaterLevelState,
    onWavesClick: () -> Unit,
    content: () -> WaterDropText
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        WaterDropLayout(
            modifier = Modifier,
            waveDurationInMills = waveDurationInMills,
            waterLevelState = waterLevelState,
            onWavesClick = onWavesClick,
            content = content
        )
    }
}