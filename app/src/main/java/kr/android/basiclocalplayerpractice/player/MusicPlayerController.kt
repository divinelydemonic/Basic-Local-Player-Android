package kr.android.basiclocalplayerpractice.player

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kr.android.basiclocalplayerpractice.R

class MusicPlayerController(
    private val context: Context
) {

    private val player = ExoPlayer.Builder(context).build()

    var onPlayingStateChanged : ((Boolean) -> Unit)? = null
    var onMetadataChanged : ((MediaMetadata) -> Unit)? = null


    init {
        player.addListener(
            object : Player.Listener{

                //what to do when isPlaying value is changed (music playing/not)
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    onPlayingStateChanged?.invoke(isPlaying)
                }

                //what to do when the music metadata has changed
                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    onMetadataChanged?.invoke(mediaMetadata)
                }

            }
        )
    }


    //loads the music for playing
    fun loadSong(){

        //extracts song address from package
        val songUri = "android.resource://${context.packageName}/${R.raw.chicago}".toUri()

        //creates a media item (box for storing the music) for music metadata
        val mediaItem = MediaItem.Builder()
            .setUri(songUri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Chicago")
                    .setAlbumTitle("Chicago")
                    .setArtist("Michael Jackson")
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

    //pauses the playing music
    fun pauseSong(){
        player.pause()
    }

    //checks if music is playing
    fun isPlaying() : Boolean {
        return player.isPlaying
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