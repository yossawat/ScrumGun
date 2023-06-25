package per.scrumgun.core.util

import java.text.SimpleDateFormat

object DateUtils {
    fun formatYearMonthDateTimeWithRandomString(): String {
        val format = "yyyyMMddHHmmss"
        return SimpleDateFormat(
            format,
            TimeUtils.getLocale()
        ).format(TimeUtils.getDate()) + TimeUtils.randomString(3)
    }
}
