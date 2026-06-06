package kr.android.basiclocalplayerpractice.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.android.basiclocalplayerpractice.view.components.AlbumArtSection
import kr.android.basiclocalplayerpractice.view.components.MusicInfoSection
import kr.android.basiclocalplayerpractice.view.components.PlaybackControlsSection
import kr.android.basiclocalplayerpractice.view.components.SeekBarSection
import kr.android.basiclocalplayerpractice.viewmodel.MusicViewModel

@Composable
fun MusicPlayerScreen(
    musicViewModel: MusicViewModel
){

    //instance of MusicUIState data class
    val uiState by musicViewModel.uiState.collectAsStateWithLifecycle()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.inversePrimary
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AlbumArtSection(
                uiState = uiState
            )

            MusicInfoSection(
                uiState = uiState
            )

            SeekBarSection(
                uiState = uiState,
                onSeek = { position ->
                    musicViewModel.seekTo(position)
                }
            )

            PlaybackControlsSection(
                uiState = uiState,
                onPlayPauseClick = { musicViewModel.togglePlayPause() },
                onNextClick = { musicViewModel.playNextSong() },
                onPreviousClick = { musicViewModel.playPreviousSong() },
                onRepeatClick = {  },
                onShuffleClick = {  },
            )

        }

    }

}