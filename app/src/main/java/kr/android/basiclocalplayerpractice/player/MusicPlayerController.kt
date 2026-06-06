package kr.android.basiclocalplayerpractice.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kr.android.basiclocalplayerpractice.model.SongData

class MusicPlayerController(
    private val context: Context
) {

    private val player = ExoPlayer.Builder(context).build()

    var onPlayingStateChanged : ((Boolean) -> Unit)? = null


    init {
        player.addListener(
            object : Player.Listener{
                //what to do when isPlaying value is changed (music playing/not)
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    onPlayingStateChanged?.invoke(isPlaying)
                }
            }
        )
    }


    //loads the music for playing
    fun loadSong(song: SongData){

        //uri of selected song
        val songUri = song.uri

        //creates a media item (box for storing the music) for music metadata
        val mediaItem = MediaItem.Builder()
            .setUri(songUri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(song.title)
                    .setAlbumTitle(song.album)
                    .setArtist(song.artist)
                    .build()
            )
            .build()


        //points to the media item that is to be played
        player.setMediaItem(mediaItem)

        //prepares the music to play
        player.prepare()

    }

    //starts playing the music
    fun playSong(){
        player.play()
    }

    //toggles play-pause button
    fun togglePlayPause(){
        if (player.isPlaying) player.pause()
        else player.play()
    }

    //current position of the music being played
    fun getCurrentPosition() : Long {
        return player.currentPosition
    }

    //total duration of the music being played
    fun getDuration() : Long {
        return player.duration
    }

    //move to sought position
    fun seekTo(position : Long){
        player.seekTo(position)
    }

}