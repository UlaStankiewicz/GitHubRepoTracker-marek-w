package pl.mwaszczuk.githubrepotracker.core.converter

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.TimeZone
import kotlin.math.absoluteValue

fun OffsetDateTime.toSystemZonedLocalDateTime(timezone: String): LocalDateTime {
    val timeZoneOffset = TimeZone.getTimeZone(ZoneId.of(timezone)).rawOffset / 1000 / 60 / 60
    val offsetSign = if (timeZoneOffset < 0) {
        "-"
    } else {
        "+"
    }
    val offsetString = "${offsetSign}0${timeZoneOffset.absoluteValue}:00"
    return this
        // Note: The way the api returns date offset is weird.
        // the start_time, end_time fields return the date with "+00:00" offset (UTC)
        // but we have additional info "timezone": "Central"
        // why not just return a date with "+06:00" offset?
        .withOffsetSameLocal(ZoneOffset.of(offsetString))
        .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}

fun LocalDateTime.datesBetween(other: LocalDateTime): List<LocalDate> {
    val higher = takeIf { isAfter(other) } ?: other
    val lower = takeIf { isBefore(other) } ?: other
    val days = higher.dayOfYear - lower.dayOfYear
    return buildList {
        for (k in 0..days) {
            add(lower.plusDays(k.toLong()).toLocalDate())
        }
    }
}
