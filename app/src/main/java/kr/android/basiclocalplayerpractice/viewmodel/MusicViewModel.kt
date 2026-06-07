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
import kr.android.basiclocalplayerpractice.utils.PlaylistMode
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

    //initializing code (runs as soon as the app starts)
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
                    //if shuffle mode is off don't play first song after last
                    //once playback finishes the seekbar seeks to 0 and pauses
                    //which can be played once again
                    else {

                        //the song list depends on which playlist we are on
                        val songs = getActivePlaylist()
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

        //loads the songs on app starting after getting permission
        loadSongs()

        //provides current position updates
        startPositionUpdates()

    }

    //loads the songs from repository
    fun loadSongs() {
        val songs = musicRepository.getAllSongs()
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

        //the song list will be determined on which playlist we are (all/favorites)
        val songs = getActivePlaylist()

        //return the song data if any song is playing
        val currentSong = _uiState.value.currentSong ?: return

        //get index of current song
        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        //if the current song is not part of the active playlist,
        //start playback from the first song in the playlist
        if (currentIndex == -1) {
            if (songs.isNotEmpty()) {
                selectSong(songs.first())
            }
            return
        }

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

        //the song list will be determined on which playlist we are (all/favorites)
        val songs = getActivePlaylist()

        //return the song data if any song is playing
        val currentSong = _uiState.value.currentSong ?: return

        //get index of current song
        val currentIndex = songs.indexOfFirst {it.id == currentSong.id }

        //if the current song is not part of the active playlist,
        //start playback from the first song in the playlist
        if (currentIndex == -1) {
            if (songs.isNotEmpty()) {
                selectSong(songs.first())
            }
            return
        }

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

    //toggle add to favorites
    fun toggleFavorite(song: SongData) {

        val favorites = _uiState.value.favoriteSongIds.toMutableSet()

        if (song.id in favorites) { favorites.remove(song.id) }
        else { favorites.add(song.id) }

        _uiState.value = _uiState.value.copy(favoriteSongIds = favorites)
    }

    //toggle playlist
    fun togglePlaylistMode() {

        val nextMode =
            when (_uiState.value.playlistMode) {
                PlaylistMode.ALL -> PlaylistMode.FAVORITES
                PlaylistMode.FAVORITES -> PlaylistMode.ALL }

        _uiState.value = _uiState.value.copy(playlistMode = nextMode)
    }

    //get the current playlist (all/favorites)
    fun getActivePlaylist(): List<SongData> {
        return if (_uiState.value.playlistMode == PlaylistMode.FAVORITES) {
            _uiState.value.songs.filter {
                it.id in _uiState.value.favoriteSongIds
            }
        } else { _uiState.value.songs }
    }

    //updates state of permission (allowed/denied)
    fun updatePermissionState(hasPermission: Boolean) {
        _uiState.value = _uiState.value.copy(hasAudioPermission = hasPermission)
    }

}