package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.utils.timeConversion

@Composable
fun SeekBarSection(
    uiState: MusicUIState,
    onSeek: (Long) -> Unit
){

    val currentPosition = uiState.currentPosition
    val duration = uiState.duration

    var sliderPosition by remember {
        mutableStateOf(0f)
    }

    var isDragging by remember {
        mutableStateOf(false)
    }

    //dynamically changing current position if the slider is being dragged
    val displayedPosition =
        if (isDragging) (sliderPosition * duration).toLong()
        else currentPosition

    //dynamically changing remaining duration
    val remainingDuration = duration - displayedPosition


    //updating slider along with the music progress
    LaunchedEffect(currentPosition, duration) {
        if (!isDragging && uiState.duration > 0){
            sliderPosition = currentPosition.toFloat()/duration.toFloat()
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Slider(
                value = sliderPosition,
                onValueChange = {
                    isDragging = true
                    sliderPosition = it
                },
                onValueChangeFinished = {
                    onSeek((sliderPosition * duration).toLong())
                    isDragging = false
                },
                colors = SliderDefaults.colors(
                    inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = timeConversion(displayedPosition),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "- ${timeConversion(remainingDuration)}",
                    style = MaterialTheme.typography.bodyLarge
                )

            }

        }

    }

}
