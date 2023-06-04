package pl.mwaszczuk.githubrepotracker.core.formatter

import android.content.Context
import pl.mwaszczuk.githubrepotracker.core.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.hours


fun LocalDateTime.formatTo(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

/**
 * Results with eg.: 8:21am, or 8:21pm
 */
fun LocalDateTime.toAMPMTime(): String {
    return formatTo(AM_PM_PATTERN)
}

/**
 * Results with eg.: 8:21am - 9:30am
 */
fun LocalDateTime.toAMPMTimeRange(other: LocalDateTime): String {
    return "${this.toAMPMTime()} - ${other.toAMPMTime()}"
}

/**
 * Returns date label in format ->  (DAY IN WEEK), (MONTH_NAME) (DAY_IN_MONTH)
 *  E.g. If date is on today it returns Today, February 12 otherwise Monday, February 12
 */
fun LocalDateTime.getDateLabel(context: Context): String {
    val dayName =
        if (this == LocalDateTime.now())
            context.getString(R.string.today)
        else
            this.dayOfWeek.name.capitalizeLowerCase()
    val monthName = this.month.name.capitalizeLowerCase()
    return "$dayName, $monthName ${this.dayOfMonth}"
}

/**
 * returns duration in hours or days. E.g. 12 hours, or 5 days
 */
fun LocalDateTime.durationTo(other: LocalDateTime, context: Context): String {
    val durationHours = until(other, ChronoUnit.HOURS).hours.inWholeHours.toInt()
    return if (durationHours < HOURS_IN_DAY) {
        context.resources.getQuantityString(
            R.plurals.duration_hours,
            durationHours,
            durationHours
        )
    } else {
        val durationDays = durationHours / HOURS_IN_DAY
        context.resources.getQuantityString(
            R.plurals.duration_days,
            durationDays,
            durationDays
        )
    }
}

const val AM_PM_PATTERN = "K:mm a"
const val HOURS_IN_DAY = 24
