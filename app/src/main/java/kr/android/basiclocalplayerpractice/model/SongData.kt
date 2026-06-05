package kr.android.basiclocalplayerpractice.model

import android.net.Uri

data class SongData(
    val id : Long,
    val title : String,
    val artist : String,
    val album : String,
    val uri : Uri
)
