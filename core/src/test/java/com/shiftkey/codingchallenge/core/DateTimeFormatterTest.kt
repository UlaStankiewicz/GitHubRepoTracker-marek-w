package pl.mwaszczuk.githubrepotracker.core

import android.content.Context
import android.content.res.Resources
import pl.mwaszczuk.githubrepotracker.core.formatter.durationTo
import pl.mwaszczuk.githubrepotracker.core.formatter.getDateLabel
import pl.mwaszczuk.githubrepotracker.core.formatter.toAMPMTime
import pl.mwaszczuk.githubrepotracker.core.formatter.toAMPMTimeRange
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class DateTimeFormatterTest {

    private val mockResources = mockk<Resources> {
        every { getQuantityString(R.plurals.duration_hours, any(), any()) } answers {
            (it.invocation.args[1] as Int).toString().plus(" hours")
        }
        every { getQuantityString(R.plurals.duration_days, any(), any()) } answers {
            (it.invocation.args[1] as Int).toString().plus(" days")
        }
    }

    private val context = mockk<Context> {
        every { getString(R.string.today) } returns  "Today"
        every { resources } returns mockResources
    }

    private val today = LocalDateTime.of(2023, 3, 11, 5, 33, 0, 0)
    private val dateTime1 = LocalDateTime.of(2023, 3, 13, 12, 12, 0, 0)
    private val dateTime2 = LocalDateTime.of(2023, 5, 20, 16, 15, 0, 0)
    private val dateTime3 = LocalDateTime.of(2023, 12, 1, 22, 51, 0, 0)
    private val dateTime4 = today.plusHours(11)

    @Before
    fun setup() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns today
    }

    @After
    fun clean() {
        unmockkAll()
    }

    @Test
    fun `should format to date label properly`() {
        val resultToday = today.getDateLabel(context)
        val resultDateTime1 = dateTime1.getDateLabel(context)
        val resultDateTime2 = dateTime2.getDateLabel(context)
        val resultDateTime3 = dateTime3.getDateLabel(context)

        val expectedResultToday = "Today, March 11"
        val expectedResultDateTime1 = "Monday, March 13"
        val expectedResultDateTime2 = "Saturday, May 20"
        val expectedResultDateTime3 = "Friday, December 1"

        assertEquals(expectedResultToday, resultToday)
        assertEquals(expectedResultDateTime1, resultDateTime1)
        assertEquals(expectedResultDateTime2, resultDateTime2)
        assertEquals(expectedResultDateTime3, resultDateTime3)
    }

    @Test
    fun `should format to AM PM time properly`() {
        val resultToday = today.toAMPMTime()
        val resultDateTime1 = dateTime1.toAMPMTime()
        val resultDateTime2 = dateTime2.toAMPMTime()
        val resultDateTime3 = dateTime3.toAMPMTime()

        val expectedResultToday = "5:33 AM"
        val expectedResultDateTime1 = "0:12 PM"
        val expectedResultDateTime2 = "4:15 PM"
        val expectedResultDateTime3 = "10:51 PM"

        assertEquals(expectedResultToday, resultToday)
        assertEquals(expectedResultDateTime1, resultDateTime1)
        assertEquals(expectedResultDateTime2, resultDateTime2)
        assertEquals(expectedResultDateTime3, resultDateTime3)
    }

    @Test
    fun `should format to AM PM time range properly`() {
        val resultRange1 = today.toAMPMTimeRange(dateTime2)
        val resultRange2 = dateTime1.toAMPMTimeRange(dateTime3)

        val expectedResultRange1 = "5:33 AM - 4:15 PM"
        val expectedResultRange2 = "0:12 PM - 10:51 PM"

        assertEquals(expectedResultRange1, resultRange1)
        assertEquals(expectedResultRange2, resultRange2)
    }

    @Test
    fun `should format to duration hours, days properly`() {
        val resultDuration1 = today.durationTo(dateTime4, context)
        val resultDuration2 = today.durationTo(dateTime1, context)

        val expectedResultDuration1 = "11 hours"
        val expectedResultDuration2 = "2 days"

        assertEquals(expectedResultDuration1, resultDuration1)
        assertEquals(expectedResultDuration2, resultDuration2)
    }
}
