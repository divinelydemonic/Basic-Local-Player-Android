package kr.android.basiclocalplayerpractice.utils

//to convert the ms(milliseconds) time into mm:ss format (minutes and seconds)
fun timeConversion(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return "%d:%02d".format(minutes, seconds)
}