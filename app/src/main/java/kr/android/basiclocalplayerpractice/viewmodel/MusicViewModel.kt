package kr.android.basiclocalplayerpractice.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kr.android.basiclocalplayerpractice.data.MusicRepository
import kr.android.basiclocalplayerpractice.model.MusicUIState
import kr.android.basiclocalplayerpractice.model.SongData
import kr.android.basiclocalplayerpractice.player.MusicPlayerController
import kotlin.time.Duration.Companion.milliseconds

class MusicViewModel(
    application: Application
) : AndroidViewModel(application) {

    //creating instance of player controller
    private val playerController = MusicPlayerController(context = getApplication())

    //creating instance of music repository
    private val musicRepository = MusicRepository(getApplication())

    //instances of MusicUIState
    private val _uiState = MutableStateFlow(MusicUIState())
    val uiState = _uiState.asStateFlow()


    init {

        //what to do when music is being played and vice versa (isPlaying changed)
        playerController.onPlayingStateChanged = { isPlaying ->
            //copying isPlaying value from player listener into UIState
            _uiState.value = _uiState.value.copy(isPlaying = isPlaying)
        }

        //provides current position updates
        startPositionUpdates()

        //storing the songs from music repository
        val songs = musicRepository.getAllSongs()

        //copying all the songs from repository to the song list in MusicUIState
        _uiState.value = _uiState.value.copy(songs = songs)

    }

    //updating the current position of the music
    private fun startPositionUpdates(){

        viewModelScope.launch {

            //as long as the coroutine is not canceled (canceled if viewmodel is destroyed)
            while (isActive) {

                //copies the current position and total duration into UIState
                _uiState.value = _uiState.value.copy(
                    currentPosition = playerController.getCurrentPosition(),
                    duration = playerController.getDuration()
                )

                //refreshes the current position of the song every 0.5s
                delay(500.milliseconds)

            }

        }

    }

    //selects a song from list for playing
    fun selectSong(song: SongData){

        //if the current song is playing then it should play/pause
        if (_uiState.value.currentSong?.id == song.id){
            playerController.togglePlayPause()
            return
        }

        _uiState.value = _uiState.value.copy(
            currentSong = song
        )

        //loads the song
        playerController.loadSong(song)

        //plays the song
        playerController.playSong()
    }

    //play/pause song
    fun togglePlayPause(){
        playerController.togglePlayPause()
    }

    //drag music slider
    fun seekTo(position: Long){
        playerController.seekTo(position)
    }

    //for skipping to next song
    fun playNextSong(){

        val songs = _uiState.value.songs
        val currentSong = _uiState.value.currentSong ?: return

        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        if (currentIndex == -1) return

        val nextIndex =
            if (currentIndex < songs.lastIndex) currentIndex + 1
            else 0

        selectSong(songs[nextIndex])

    }

    //for getting to the previous song
    fun playPreviousSong(){

        val songs = _uiState.value.songs
        val currentSong = _uiState.value.currentSong ?: return

        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        if (currentIndex == -1) return

        val previousIndex =
            if (currentIndex > 0) currentIndex - 1
            else songs.lastIndex

        selectSong(songs[previousIndex])

    }

}