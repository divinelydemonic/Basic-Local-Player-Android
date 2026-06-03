@file:OptIn(ExperimentalMaterial3Api::class)

package kr.android.basiclocalplayerpractice

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier
){

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            //todo music player
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
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
        content = {
            //todo song list
        }
    )

}