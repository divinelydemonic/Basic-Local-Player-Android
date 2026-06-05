package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState

@Composable
fun AlbumArtSection(
    uiState: MusicUIState
){

    //todo extract song album art
    Box(
        modifier = Modifier
            .size(350.dp)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        if (uiState.albumArt != null) {
            Image(
                bitmap = uiState.albumArt.asImageBitmap(),
                contentDescription = "album art",
                modifier = Modifier.fillMaxSize()
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
