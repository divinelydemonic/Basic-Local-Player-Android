package kr.android.basiclocalplayerpractice.model

import kr.android.basiclocalplayerpractice.utils.RepeatModes

data class MusicUIState(
    val currentPosition : Long = 0L,
    val duration : Long = 0L,
    val repeatModes: RepeatModes = RepeatModes.OFF,
    val shuffleMode : Boolean = false
)
