package com.exyte.wave.waterdrops

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntSize
import com.exyte.wave.animating.WaterLevelState
import com.exyte.wave.animating.waveProgressAsState
import com.exyte.wave.ui.theme.Water
import com.exyte.wave.waterdrops.canvas.drawTextWithBlendMode
import com.exyte.wave.waterdrops.canvas.drawWaves
import com.exyte.wave.waterdrops.text.createTextParamsAsState
import com.exyte.wave.waterdrops.wave.WaterDropText
import com.exyte.wave.waterdrops.wave.WaveParams
import com.exyte.wave.waterdrops.wave.createAnimationsAsState

@Composable
fun WaterDropLayout(
    modifier: Modifier = Modifier,
    waveDurationInMills: Long = 6000L,
    waterLevelState: WaterLevelState,
    onWavesClick: () -> Unit,
    waveParams: WaveParams,
) {
    val waveParams = remember { waveParams }
    val animations = createAnimationsAsState(pointsQuantity = waveParams.pointsQuantity)
    WaterLevelDrawing(
        modifier = modifier,
        waveDurationInMills = waveDurationInMills,
        waveParams = waveParams,
        animations = animations,
        waterLevelState = waterLevelState,
        onWavesClick = onWavesClick,
    )
}

@Composable
fun WaterLevelDrawing(
    modifier: Modifier = Modifier,
    waveDurationInMills: Long,
    waveParams: WaveParams,
    animations: MutableList<State<Float>>,
    waterLevelState: WaterLevelState,
    onWavesClick: () -> Unit,
) {
    val waveDuration by rememberSaveable { mutableStateOf(waveDurationInMills) }
    val waveProgress by waveProgressAsState(
        timerState = waterLevelState,
        timerDurationInMillis = waveDuration
    )
    WavesDrawing(
        modifier = modifier,
        waveDuration = waveDuration,
        animations = animations,
        waveProgress = waveProgress,
        waveParams = waveParams,
        onWavesClick = onWavesClick,
    )
}

@Composable
fun WavesDrawing(
    modifier: Modifier = Modifier,
    waveDuration: Long,
    waveParams: WaveParams,
    animations: MutableList<State<Float>>,
    waveProgress: Float,
    onWavesClick: () -> Unit,
) {
    val elementParams by remember { mutableStateOf(ElementParams()) }
    var containerSize by remember { mutableStateOf(IntSize(0, 0)) }

    val dropWaterDuration = rememberDropWaterDuration(
        elementSize = elementParams.size,
        containerSize = containerSize,
        duration = waveDuration
    )

    // Reverse the water level calculation - subtract from container height
    val waterLevel by remember(waveProgress, containerSize.height) {
        derivedStateOf {
            ((1 - waveProgress) * containerSize.height).toInt()
        }
    }

    // Create a separate element params for the wave that doesn't depend on text position
    val waveElementParams = ElementParams(
        // Use fixed positions that don't depend on container size or text
        position = Offset(0f, 0f),
        size = IntSize(
            width = containerSize.width,
            height = containerSize.height
        )
    )

    val levelState = createLevelAsState(
        waterLevelProvider = { 1 },  // here is what prevent jumping when target is >= 0.7 and animation reach at that stage -> using waterLevel cause problem!
        bufferY = waveParams.bufferY,
        elementParams = waveElementParams
    )

    val paths = createPathsAsState(
        containerSize = containerSize,
        elementParams = waveElementParams,
        levelState = levelState.value,
        waterLevelProvider = { waterLevel.toFloat() },
        dropWaterDuration = dropWaterDuration,
        animations = animations,
        waveParams = waveParams
    )

    Canvas(
        modifier = Modifier
            .background(Water)
            .fillMaxSize()
    ) {
        drawWaves(paths)
    }

    Box(
        modifier = modifier
            .clickable(onClick = onWavesClick)
            .onGloballyPositioned { containerSize = IntSize(it.size.width, it.size.height) }
    ) {
//        Text(
//            modifier = content().modifier
//                .align(content().align)
//                .onGloballyPositioned {
//                    elementParams.position = it.positionInParent()
//                    elementParams.size = it.size
//                },
//            text = "46FT",
//            style = content().textStyle
//        )
    }
}

@Stable
data class ElementParams(
    var size: IntSize = IntSize.Zero,
    var position: Offset = Offset(0f, 0f),
)

data class Paths(
    val pathList: MutableList<Path> = mutableListOf(Path(), Path())
)