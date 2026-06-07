package kr.android.basiclocalplayerpractice.model

import kr.android.basiclocalplayerpractice.utils.PlaylistMode
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
    val repeatMode: RepeatModes = RepeatModes.OFF,
    val shuffleMode : Boolean = false,

    //list of all songs
    val songs : List<SongData> = emptyList(),

    //list of favorite songs
    val favoriteSongIds: Set<Long> = emptySet(),

    //playlist type
    val playlistMode: PlaylistMode = PlaylistMode.ALL,

    //check is audio permission is available
    val hasAudioPermission: Boolean = true,

)
