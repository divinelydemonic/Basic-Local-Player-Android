package kr.android.basiclocalplayerpractice.view.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.model.SongData

@Composable
fun MusicList(
    modifier: Modifier,
    uiState: MusicUIState,
    onSongClick : (SongData) -> Unit
){

    Log.d(
        "MusicList",
        "Songs displayed: ${uiState.songs.size}"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(uiState.songs){ song ->
            MusicListItem(
                uiState = uiState,
                song = song,
                onSongClick = onSongClick,
                isCurrentSong = song.id == uiState.currentSong?.id
            )
        }

    }

}