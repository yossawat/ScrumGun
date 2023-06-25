package per.scrumgun.core.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun getTimeStamp(): String = getTimeStamp(getDate())

    fun getTimeStamp(date: Date? = null): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getLocale()).format(date ?: getDate())

    fun getDate(): Date = Date(getCalendar().timeInMillis)

    fun getCalendar(): Calendar = Calendar.getInstance(getLocale())

    fun getLocale(): Locale = Locale("TH")

    fun randomString(length: Int): String {
        val stringRandom = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        val builder = StringBuilder()
        for (i in 0 until length) {
            val index = Random().nextInt(stringRandom.length)
            builder.append(stringRandom[index])
        }
        return builder.toString()
    }
}
