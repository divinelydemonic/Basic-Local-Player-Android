package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.utils.RepeatModes

@Composable
fun PlaybackControlsSection(
    uiState: MusicUIState,
    onPlayPauseClick : () -> Unit,
    onNextClick : () -> Unit,
    onPreviousClick : () -> Unit,
    onRepeatClick : () -> Unit,
    onShuffleClick : () -> Unit
) {

    //extracting play-pause button
    val playPauseIcon =
        if (uiState.isPlaying) Icons.Default.Pause
        else Icons.Default.PlayArrow


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            //repeat mode button
            IconButton(
                onClick = onRepeatClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector =
                        when (uiState.repeatMode) {
                            RepeatModes.OFF -> Icons.Default.Repeat
                            RepeatModes.ONE -> Icons.Default.RepeatOne
                            RepeatModes.ALL -> Icons.Default.Repeat
                        },
                    contentDescription = "repeat mode",
                    tint =
                        when (uiState.repeatMode) {
                            RepeatModes.OFF -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            RepeatModes.ONE -> MaterialTheme.colorScheme.onSurface
                            RepeatModes.ALL -> MaterialTheme.colorScheme.onSurface
                        },
                    modifier = Modifier.size(35.dp)
                )
            }

            //previous track button
            IconButton(
                onClick = onPreviousClick,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "previous",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(50.dp)
                )
            }

            //play-pause button
            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = playPauseIcon,
                    contentDescription = "play-pause",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(50.dp)
                )
            }

            //next track button
            IconButton(
                onClick = onNextClick,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "next",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(50.dp)
                )
            }

            //shuffle mode button
            IconButton(
                onClick = onShuffleClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = "shuffle mode",
                    tint =
                        if (uiState.shuffleMode) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    modifier = Modifier.size(35.dp)
                )
            }

        }

    }

}
