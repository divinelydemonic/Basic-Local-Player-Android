package kr.android.basiclocalplayerpractice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.player.MusicPlayerController

class MusicViewModel(
    application: Application
) : AndroidViewModel(application) {

    //creating instance of player controller
    private val playerController = MusicPlayerController(context = getApplication())

    private val _uiState = MutableStateFlow(MusicUIState())
    val uiState = _uiState.asStateFlow()


    init {

        playerController.onPlayingStateChanged = { isPlaying ->

            //copying isPlaying value from ExoPlayer into UIState
            _uiState.value = _uiState.value.copy(
                isPlaying = isPlaying
            )

        }

        //todo remove (for testing)
        //loads the available song
        playerController.loadSong()

    }

    //accessed methods from player controller

    fun loadSong(){
        playerController.loadSong()
    }

    fun togglePlayPause(){
        playerController.togglePlayPause()
    }


}