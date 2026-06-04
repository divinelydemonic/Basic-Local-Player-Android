package kr.android.basiclocalplayerpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kr.android.basiclocalplayerpractice.player.MusicPlayerController
import kr.android.basiclocalplayerpractice.ui.theme.BasicLocalPlayerPracticeTheme
import kr.android.basiclocalplayerpractice.view.MusicPlayerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            BasicLocalPlayerPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MusicPlayerScreen(
                        modifier = Modifier.padding(innerPadding),
                        playerController = MusicPlayerController(context)
                    )
                }
            }
        }
    }
}