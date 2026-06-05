package kr.android.basiclocalplayerpractice.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

fun hasAudioPermission(context: Context) : Boolean {

    //checking if android version is 13 or newer (Tiramisu is Android 13)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        //android version >= 13
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }
        //android version < 13
        else {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}