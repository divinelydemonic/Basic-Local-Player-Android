package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.android.basiclocalplayerpractice.R
import kr.android.basiclocalplayerpractice.model.MusicUIState

@Composable
fun AlbumArtSection(
    uiState: MusicUIState
){

    val currentSong = uiState.currentSong

    Box(
        modifier = Modifier
            .size(350.dp)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        if (currentSong?.albumArtUri != null) {
            AsyncImage(
                model = currentSong.albumArtUri,
                contentDescription = "album art",
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.fallback_album_art),
                error = painterResource(R.drawable.fallback_album_art),
                fallback = painterResource(R.drawable.fallback_album_art)
            )
        } else {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "fallback album art",
                modifier = Modifier.size(150.dp)
            )
        }
    }

}
