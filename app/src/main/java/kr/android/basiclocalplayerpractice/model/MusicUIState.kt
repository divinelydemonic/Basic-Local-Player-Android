package kr.android.basiclocalplayerpractice.model

import android.graphics.Bitmap
import kr.android.basiclocalplayerpractice.utils.RepeatModes

data class MusicUIState(

    //music info
    val isPlaying : Boolean = false,
    val currentPosition : Long = 0L,
    val duration : Long = 0L,

    //music metadata
    val songTitle : String = "",
    val artistName : String = "",
    val albumName : String = "",
    val albumArt : Bitmap? = null,

    //playback modes
    val repeatModes: RepeatModes = RepeatModes.OFF,
    val shuffleMode : Boolean = false,

)
