package kr.android.basiclocalplayerpractice.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.core.net.toUri
import kr.android.basiclocalplayerpractice.model.SongData

class MusicRepository(
    private val context: Context
) {

    //access all songs in local storage
    fun getAllSongs() : List<SongData> {

        //storing the list of songs
        val songs = mutableListOf<SongData>()

        //stores location of all audio files (URI)
        val audioCollection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI


        //projection is the attributes we need from a table
        //array of info we need from the metadata
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID
        )   //creates a table of all the columns we need

        //filters out non-audio files (recordings, ringtones, etc.)
        val selection = "${ MediaStore.Audio.Media.IS_MUSIC } != 0"

        //using query in contentResolver for getting the songs (returns Cursor?)
        val cursor = context.contentResolver.query(
            audioCollection,    //which table (address)
            projection,              //which columns/attributes needed
            selection,               //no filtering (provides everything)
            null,     //no arguments for filter since no filtering used
            null         //no sorting order
        )
        //creates a cursor (like a pointer) that is positioned above the first row and knows
        //about all the rows, and it gradually moves to the next rows for reading the values

        //reading the columns of a particular row from result
        cursor?.use { cursor -> //use cursor if available else skip it

            //getting column indexes for the attributes taken
            //provides indexes to the columns for the cursor to read the values

            val idColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media._ID
                )

            val titleColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.TITLE
                )

            val artistColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.ARTIST
                )

            val albumColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.ALBUM
                )

            val albumIdColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.ALBUM_ID
                )


            while (cursor.moveToNext()){   //moves to the next row till there's a row to read

                //storing the values from reading the columns using column indexes
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
                val albumId = cursor.getLong(albumIdColumn)

                //creating uri for the album art for providing UI to show
                val albumArtUri =
                    ContentUris.withAppendedId(
                        "content://media/external/audio/albumart".toUri(),
                        albumId
                    )

                //creating uri for the song for providing ExoPlayer to play
                val uri = ContentUris.withAppendedId(
                    audioCollection,    //location of song
                    id                              //id of song
                )   //combines both and creates uri of a song (Uniform Resource Identifier)

                //creating song data for displaying
                songs.add(
                    SongData(
                        id = id,
                        title = title,
                        artist = artist,
                        album = album,
                        uri = uri,
                        albumId = albumId,
                        albumArtUri = albumArtUri
                    )
                )
            }

        }

        return songs
    }

}