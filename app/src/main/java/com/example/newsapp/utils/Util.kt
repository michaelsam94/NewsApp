package com.example.newsapp.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun dateFormat(oldStringDate: String?): String? {
    if (oldStringDate == null || oldStringDate == "")
        return ""
    val newDate: String?
    val dateFormat = SimpleDateFormat("E, d MMM yyyy", getCountry()?.let { Locale(it) })
    newDate = try {
        val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate)
        dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            oldStringDate
        }
        return newDate
    }

private fun getCountry(): String? =
    Locale.getDefault().country.toLowerCase(Locale.ROOT)


fun getLanguage(): String? = Locale.getDefault().language


// ex : convert time to  2 hour ago
fun dateToTimeFormat(oldStringDate: String?): String? {
    val p = PrettyTime(Locale(getCountry()))
    var isTime: String? = null
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date: Date = sdf.parse(oldStringDate)
        isTime = p.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return isTime
}

