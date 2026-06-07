@file:OptIn(ExperimentalMaterial3Api::class)

package kr.android.basiclocalplayerpractice.view

import android.Manifest
import android.os.Build
import android.widget.Toast
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kr.android.basiclocalplayerpractice.MainActivity
import kr.android.basiclocalplayerpractice.utils.PlaylistMode
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
    val scope = rememberCoroutineScope()
    val isExpanded =
        scaffoldState.bottomSheetState.targetValue == SheetValue.Expanded

    val uiState by musicViewModel.uiState.collectAsStateWithLifecycle()
    val hasCurrentSong = uiState.currentSong != null
    val favoriteCount = uiState.favoriteSongIds.size
    val displayedSongs = musicViewModel.getActivePlaylist()

    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ){ permissionGranted ->

            //load the songs and set permissionState as true if permission is given
            if (permissionGranted) {
                musicViewModel.updatePermissionState(true)
                musicViewModel.loadSongs()
            }
            //if permission not given show toast
            else {

                val permission =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        Manifest.permission.READ_MEDIA_AUDIO
                    else
                        Manifest.permission.READ_EXTERNAL_STORAGE

                val rationaleRequired =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context as MainActivity,
                        permission
                    )

                if (rationaleRequired) {

                    Toast.makeText(
                        context,
                        "Music access is required to load and play songs.",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "Music access is permanently denied. Please enable it from Settings.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }


    //launch request launcher
    LaunchedEffect(Unit) {
        if (hasAudioPermission(context)) {
            //if permission given set permissionState to true
            musicViewModel.updatePermissionState(true)
        } else {
            //if permission given set permissionState to false and launch permission launcher
            musicViewModel.updatePermissionState(false)
            permissionLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_AUDIO
                else Manifest.permission.READ_EXTERNAL_STORAGE)
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
                onFavoriteClick = {
                    musicViewModel.togglePlaylistMode()
                },
                isFavoriteFilterEnabled =
                    uiState.playlistMode == PlaylistMode.FAVORITES,
                favoriteCount = favoriteCount
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = { paddingValues ->
            MusicList(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                displayedSongs = displayedSongs,
                onSongClick = { song ->
                    musicViewModel.selectSong(song)
                },
                onFavoriteClick = { song ->
                    musicViewModel.toggleFavorite(song)
                }
            )
        }
    )

}