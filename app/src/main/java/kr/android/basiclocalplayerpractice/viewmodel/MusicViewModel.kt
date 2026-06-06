package kr.android.basiclocalplayerpractice.viewmodel

import android.app.Application
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
import kr.android.basiclocalplayerpractice.utils.RepeatModes
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

        //what to do when the current song finishes playing
        playerController.onSongEnded = {

            when (_uiState.value.repeatMode) {

                //restart the same song when repeat-one is enabled
                RepeatModes.ONE -> {

                    val currentSong = _uiState.value.currentSong

                    if (currentSong != null) {
                        selectSong(currentSong)
                    }
                }

                //continue playback through the entire playlist and loop back to the start
                RepeatModes.ALL -> { playNextSong() }

                //stop playback after the last song instead of looping
                RepeatModes.OFF -> {

                    //if shuffle mode is on, play as usual
                    if (_uiState.value.shuffleMode) { playNextSong() }
                    //if shuffle mode is of don't play first song after last
                    //once playback finishes the seekbar seeks to 0 and pauses
                    //which can be played once again
                    else {

                        val songs = _uiState.value.songs
                        val currentSong = _uiState.value.currentSong

                        if (currentSong != null) {

                            val currentIndex = songs.indexOfFirst { it.id == currentSong.id }

                            if (currentIndex < songs.lastIndex) { playNextSong() }
                            else {
                                playerController.pauseSong()
                                _uiState.value = _uiState.value.copy(currentPosition = 0L)
                                playerController.seekTo(0)
                            }
                        }
                    }
                }
            }
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

        _uiState.value = _uiState.value.copy(currentSong = song)

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

        //return the song data if any song is playing
        val currentSong = _uiState.value.currentSong ?: return

        //get index of current song
        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        //if no song playing do nothing
        if (currentIndex == -1) return

        //get next song index
        val nextIndex =
            //if shuffle mode is on, randomize next song index
            if (_uiState.value.shuffleMode) { songs.indices.random() }
            //if shuffle mode is off, go to next song index serially
            //if last song get first song index
            else {
                if (currentIndex < songs.lastIndex) currentIndex + 1
                else 0
            }

        //select next song from next index
        selectSong(songs[nextIndex])

    }

    //for getting to the previous song
    fun playPreviousSong(){

        val songs = _uiState.value.songs

        //return the song data if any song is playing
        val currentSong = _uiState.value.currentSong ?: return

        //get index of current song
        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        //if no song playing do nothing
        if (currentIndex == -1) return

        //get index of previous song
        //if first song get last song index
        val previousIndex =
            if (currentIndex > 0) currentIndex - 1
            else songs.lastIndex

        //select previous song from previous index
        selectSong(songs[previousIndex])

    }

    //toggling repeat modes
    fun toggleRepeatMode() {

        val nextMode =
            when (_uiState.value.repeatMode) {
                RepeatModes.OFF -> RepeatModes.ALL
                RepeatModes.ALL -> RepeatModes.ONE
                RepeatModes.ONE -> RepeatModes.OFF
            }

        _uiState.value = _uiState.value.copy(repeatMode = nextMode)
    }

    //toggling shuffle modes
    fun toggleShuffleMode() {
        _uiState.value = _uiState.value.copy(
            shuffleMode = !_uiState.value.shuffleMode
        )
    }

}