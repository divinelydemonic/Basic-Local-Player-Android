package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.utils.RepeatModes

@Composable
fun PlaybackControlsSection(
    uiState: MusicUIState
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
                onClick = {
                    //todo repeat mode switch
                }
            ) {
                Icon(
                    imageVector =
                        when (uiState.repeatModes) {
                            RepeatModes.OFF -> Icons.Default.Repeat
                            RepeatModes.ONE -> Icons.Default.RepeatOne
                            RepeatModes.ALL -> Icons.Default.Repeat
                        },
                    contentDescription = "repeat mode",
                    tint =
                        when (uiState.repeatModes) {
                            RepeatModes.OFF -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            RepeatModes.ONE -> MaterialTheme.colorScheme.onSurface
                            RepeatModes.ALL -> MaterialTheme.colorScheme.onSurface
                        },
                    modifier = Modifier.size(35.dp)
                )
            }

            //previous track button
            IconButton(
                onClick = {
                    //todo previous track
                }
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
                onClick = {
                    //todo play-pause
                }
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
                onClick = {
                    //todo next track
                }
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
                onClick = {
                    //todo shuffle mode toggle
                }
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

@Preview(showBackground = true)
@Composable
fun PlaybackPrev(){
    PlaybackControlsSection(
        uiState = MusicUIState()
    )
}