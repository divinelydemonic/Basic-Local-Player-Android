@file:OptIn(ExperimentalMaterial3Api::class)

package kr.android.basiclocalplayerpractice.view

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.android.basiclocalplayerpractice.model.SongData
import kr.android.basiclocalplayerpractice.utils.hasAudioPermission
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
            MusicPlayerScreen(viewModel = musicViewModel)
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetShape =
            if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded)
                 RoundedCornerShape(0.dp)
            else RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = MaterialTheme.colorScheme.inversePrimary,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 12.dp,
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