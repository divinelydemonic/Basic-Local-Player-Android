package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.android.basiclocalplayerpractice.R
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.model.SongData

@Composable
fun MusicListItem(
    uiState: MusicUIState,
    song: SongData,
    onSongClick : (SongData) -> Unit,
    onFavoriteClick : (SongData) -> Unit,
    isCurrentSong : Boolean
){

    val isPlayingColor by animateColorAsState(
        targetValue =
            if (isCurrentSong) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(50),
        label = "isPlayingColor"
    )

    val isFavorite = song.id in uiState.favoriteSongIds

    val favoriteIcon =
        if (isFavorite) Icons.Default.Favorite
        else Icons.Default.FavoriteBorder


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    if (isCurrentSong) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
            ),
            onClick = { onSongClick(song) }
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .border(
                            width = 2.dp,
                            color =
                                if (isCurrentSong) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    AsyncImage(
                        model = song.albumArtUri,
                        contentDescription = "album art",
                        error = painterResource(R.drawable.fallback_album_art),
                        placeholder = painterResource(R.drawable.fallback_album_art),
                        fallback = painterResource(R.drawable.fallback_album_art),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, top = 8.dp, bottom = 8.dp)
                ) {

                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = isPlayingColor
                    )

                    Text(
                        text = "${song.artist} • ${song.album}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color =
                            if (isCurrentSong) isPlayingColor.copy(alpha = 0.8f)
                            else MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                Box(
                    modifier = Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        onClick = { onFavoriteClick(song) },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = favoriteIcon,
                            contentDescription = "favorite",
                            tint =
                                if (isFavorite) Color(0xFFF17979)
                                else isPlayingColor,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }

        }

    }

}
