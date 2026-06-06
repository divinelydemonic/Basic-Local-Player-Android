@file:OptIn(ExperimentalMaterial3Api::class)

package kr.android.basiclocalplayerpractice.view

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kr.android.basiclocalplayerpractice.utils.hasAudioPermission
import kr.android.basiclocalplayerpractice.view.components.MiniPlayer
import kr.android.basiclocalplayerpractice.view.components.MusicList
import kr.android.basiclocalplayerpractice.view.components.TopBar
import kr.android.basiclocalplayerpractice.viewmodel.MusicViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    musicViewModel: MusicViewModel
){

    val scaffoldState = rememberBottomSheetScaffoldState()

    val uiState by musicViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val isExpanded =
        scaffoldState.bottomSheetState.targetValue == SheetValue.Expanded

    val hasCurrentSong = uiState.currentSong != null

    val context = LocalContext.current

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


    BottomSheetScaffold(
        sheetContent = {
            if (isExpanded) {
                MusicPlayerScreen(musicViewModel = musicViewModel)
            } else {
                MiniPlayer(
                    uiState = uiState,
                    onPlayPauseClick = { musicViewModel.togglePlayPause() },
                    onNextClick = { musicViewModel.playNextSong() },
                    onMiniPlayerClick = {
                        scope.launch { scaffoldState.bottomSheetState.expand() }
                    }
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight =
            if (hasCurrentSong) 108.dp
            else 0.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = MaterialTheme.colorScheme.inversePrimary,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 12.dp,
        sheetDragHandle = {
            Spacer(Modifier.height(16.dp))
        },
        sheetSwipeEnabled = true,
        topBar = {
            TopBar(
                title = "Local Music Player",
                onBackClick = {
                    //todo back navigation
                },
                onFavoriteClick = {
                    //todo favorite screen navigation
                }
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = { paddingValues ->
            MusicList(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onSongClick = { song ->
                    musicViewModel.selectSong(song)
                }
            )
        }
    )

}