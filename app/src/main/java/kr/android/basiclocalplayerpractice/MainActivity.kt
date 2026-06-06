package kr.android.basiclocalplayerpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.android.basiclocalplayerpractice.ui.theme.BasicLocalPlayerPracticeTheme
import kr.android.basiclocalplayerpractice.view.HomeScreen
import kr.android.basiclocalplayerpractice.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val musicViewModel : MusicViewModel = viewModel()

            BasicLocalPlayerPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        musicViewModel = musicViewModel
                    )
                }
            }
        }
    }
}