package kr.android.basiclocalplayerpractice.view

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.android.basiclocalplayerpractice.utils.hasAudioPermission
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

    val context = LocalContext.current

    //instance of MusicUIState data class
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
    ){ permissionGranted ->
        //todo customise permission launcher
    }

    //launch request launcher
    LaunchedEffect(Unit) {

        if (!hasAudioPermission(context)){
            permissionLauncher.launch(

                //if android version >= 13
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_AUDIO

                //if android version < 13
                else Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    }


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
                viewModel.seekTo(position)
            }
        )

        PlaybackControlsSection(
            uiState = uiState,
            onPlayPauseClick = { viewModel.togglePlayPause() }
        )

    }

}