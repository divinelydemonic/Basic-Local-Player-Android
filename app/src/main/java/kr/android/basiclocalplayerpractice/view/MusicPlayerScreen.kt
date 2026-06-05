package kr.android.basiclocalplayerpractice.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.android.basiclocalplayerpractice.view.components.AlbumArtSection
import kr.android.basiclocalplayerpractice.view.components.MusicInfoSection
import kr.android.basiclocalplayerpractice.view.components.PlaybackControlsSection
import kr.android.basiclocalplayerpractice.view.components.SeekBarSection
import kr.android.basiclocalplayerpractice.viewmodel.MusicViewModel

@Composable
fun MusicPlayerScreen(
    modifier: Modifier,
    viewModel: MusicViewModel
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                viewModel.seekTo(position)
            }
        )

        PlaybackControlsSection(
            uiState = uiState,
            onPlayPauseClick = { viewModel.togglePlayPause() }
        )

    }

}