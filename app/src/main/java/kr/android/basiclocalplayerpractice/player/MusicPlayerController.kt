package kr.android.basiclocalplayerpractice.player

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kr.android.basiclocalplayerpractice.R

class MusicPlayerController(
    private val context: Context
) {

    private val player = ExoPlayer.Builder(context).build()

    var onPlayingStateChanged : ((Boolean) -> Unit)? = null


    init {
        player.addListener(
            object : Player.Listener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    onPlayingStateChanged?.invoke(isPlaying)
                }
            }
        )
    }


    //loads the music for playing
    fun loadSong(){

        //extracts song address from package
        val songUri = "android.resource://${context.packageName}/${R.raw.chicago}".toUri()

        //creates a media item (box for storing the music)
        val mediaItem = MediaItem.fromUri(songUri)

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

}