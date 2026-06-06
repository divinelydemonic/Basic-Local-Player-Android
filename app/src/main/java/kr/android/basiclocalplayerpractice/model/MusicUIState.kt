package kr.android.basiclocalplayerpractice.model

import android.graphics.Bitmap
import kr.android.basiclocalplayerpractice.utils.RepeatModes

//playback state -> dynamic
data class MusicUIState(

    //music info
    val isPlaying : Boolean = false,
    val currentPosition : Long = 0L,
    val duration : Long = 0L,

    //point which song is playing
    val currentSong: SongData? = null,

    //playback modes
    val repeatModes: RepeatModes = RepeatModes.OFF,
    val shuffleMode : Boolean = false,

    //list of songs
    val songs : List<SongData> = emptyList()

)
