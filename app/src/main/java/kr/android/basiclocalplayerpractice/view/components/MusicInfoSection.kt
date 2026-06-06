package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kr.android.basiclocalplayerpractice.model.MusicUIState

@Composable
fun MusicInfoSection(uiState: MusicUIState){

    val currentSong = uiState.currentSong

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = currentSong?.title ?: "Unknown",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.basicMarquee()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = currentSong?.artist ?: "Unknown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = 1,
                modifier = Modifier
                    .widthIn(max = 170.dp)
                    .basicMarquee()
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "·",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = currentSong?.album ?: "Unknown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = 1,
                modifier = Modifier
                    .width(170.dp)
                    .basicMarquee()
            )
        }

    }

}