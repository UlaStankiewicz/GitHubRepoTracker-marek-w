package pl.mwaszczuk.githubrepotracker.core.formatter

import java.util.*

fun String.capitalizeLowerCase() = this
    .lowercase()
    .replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ROOT)
        else it.toString()
    }

fun String.prependUSBaseTimeZone() = this
    .prependIndent("US/")
