package kr.android.basiclocalplayerpractice.view.components

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
import kr.android.basiclocalplayerpractice.utils.EmptyState
import kr.android.basiclocalplayerpractice.model.PlaylistMode

@Composable
fun MusicList(
    modifier: Modifier,
    uiState: MusicUIState,
    displayedSongs: List<SongData>,
    onSongClick : (SongData) -> Unit,
    onFavoriteClick : (SongData) -> Unit
){

    when {

        //no audio permission
        !uiState.hasAudioPermission -> {
            // no audio permission empty state
            EmptyState(
                title = "🎵 Audio Permission Required",
                description = "Grant audio permission to browse and play songs"
            )
        }

        //no favorites
        displayedSongs.isEmpty() &&
                uiState.playlistMode == PlaylistMode.FAVORITES -> {
            // no favorites empty state
            EmptyState(
                title = "❤\uFE0F No favorite songs yet\n",
                description = "Tap the heart icon on any song\n" +
                        "to add it to your favorites."
            )
        }

        //no music/songs in storage
        displayedSongs.isEmpty() -> {
            // no songs empty state
            EmptyState(
                title = "🎶 No songs here yet\n",
                description = "Download some to get started"
            )
        }

        //all/favorite songs list
        else -> {

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(displayedSongs){ song ->
                    MusicListItem(
                        uiState = uiState,
                        song = song,
                        onSongClick = onSongClick,
                        onFavoriteClick = onFavoriteClick,
                        isCurrentSong = song.id == uiState.currentSong?.id
                    )
                }

            }

        }
    }

}