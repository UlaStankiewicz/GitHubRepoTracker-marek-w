package pl.mwaszczuk.githubrepotracker.core.formatter

import android.content.Context
import pl.mwaszczuk.githubrepotracker.core.R
import java.time.LocalDate

/**
 * Returns date label in format ->  (DAY IN WEEK), (MONTH_NAME) (DAY_IN_MONTH)
 *  E.g. If date is on today it returns Today, February 12 otherwise Monday, February 12
 */
fun LocalDate.getDateLabel(context: Context): String {
    val dayName =
        if (this == LocalDate.now())
            context.getString(R.string.today)
        else
            this.dayOfWeek.name.capitalizeLowerCase()
    val monthName = this.month.name.capitalizeLowerCase()
    return "$dayName, $monthName ${this.dayOfMonth}"
}
