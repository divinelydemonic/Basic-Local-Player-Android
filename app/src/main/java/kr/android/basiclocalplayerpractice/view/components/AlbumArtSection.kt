package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AlbumArtSection(

){

    //todo extract song album art
    Box(
        modifier = Modifier
            .size(350.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = "album art",
            modifier = Modifier.size(150.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun AlbumPrev(){
    AlbumArtSection()
}