@file:OptIn(ExperimentalMaterial3Api::class)

package kr.android.basiclocalplayerpractice.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title : String,
    onFavoriteClick : () -> Unit = {},
    isFavoriteFilterEnabled: Boolean,
    favoriteCount: Int
){

    TopAppBar(
        title = {
            Text(
                text =
                    if (isFavoriteFilterEnabled) "Favorites ($favoriteCount)"
                    else title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        actions = {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Icon(
                    imageVector =
                        if (isFavoriteFilterEnabled) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                    contentDescription = "back",
                    tint =
                        if (isFavoriteFilterEnabled) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )

}