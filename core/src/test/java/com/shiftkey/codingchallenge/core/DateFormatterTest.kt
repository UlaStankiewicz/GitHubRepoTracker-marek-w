package pl.mwaszczuk.githubrepotracker.core

import android.content.Context
import pl.mwaszczuk.githubrepotracker.core.formatter.getDateLabel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class DateFormatterTest {

    private val context = mockk<Context> {
        every { getString(any()) } returns  "Today"
    }

    private val today = LocalDate.of(2023, 3, 11)
    private val date1 = LocalDate.of(2023, 3, 13)
    private val date2 = LocalDate.of(2023, 5, 20)
    private val date3 = LocalDate.of(2023, 12, 1)

    private val expectedResultToday = "Today, March 11"
    private val expectedResultDate1 = "Monday, March 13"
    private val expectedResultDate2 = "Saturday, May 20"
    private val expectedResultDate3 = "Friday, December 1"

    @Before
    fun setup() {
        mockkStatic(LocalDate::class)
        every { LocalDate.now() } returns today
    }

    @After
    fun clean() {
        unmockkAll()
    }

    @Test
    fun `should format to date label properly`() {
        val resultToday = today.getDateLabel(context)
        val resultDate1 = date1.getDateLabel(context)
        val resultDate2 = date2.getDateLabel(context)
        val resultDate3 = date3.getDateLabel(context)

        assertEquals(expectedResultToday, resultToday)
        assertEquals(expectedResultDate1, resultDate1)
        assertEquals(expectedResultDate2, resultDate2)
        assertEquals(expectedResultDate3, resultDate3)
    }
}
