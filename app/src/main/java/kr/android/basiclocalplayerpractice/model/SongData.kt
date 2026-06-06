package kr.android.basiclocalplayerpractice.model

import android.net.Uri

//properties of a song (metadata) -> static
data class SongData(
    val id : Long,
    val title : String,
    val artist : String,
    val album : String,
    val uri : Uri,
    val albumId : Long,
    val albumArtUri : Uri?
)
