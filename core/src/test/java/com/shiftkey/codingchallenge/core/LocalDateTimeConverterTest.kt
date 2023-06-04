package pl.mwaszczuk.githubrepotracker.core

import pl.mwaszczuk.githubrepotracker.core.converter.datesBetween
import pl.mwaszczuk.githubrepotracker.core.converter.toSystemZonedLocalDateTime
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import java.time.*
import kotlin.test.assertEquals

class LocalDateTimeConverterTest {

    private val alaskaTimeZone = "US/Alaska"
    private val centralTimeZone = "US/Central"
    private val tokyoTimeZone = "Asia/Tokyo"
    private val utcTimeZone = "UTC"

    private val offsetDateTime = OffsetDateTime.of(2023, 3, 11, 12, 0, 0, 0, ZoneOffset.of("+00:00"))

    @After
    fun clean() {
        unmockkAll()
    }

    @Test
    fun `should convert offset date time to local device zone 1`() {
        mockkStatic(ZoneId::class)
        every { ZoneId.systemDefault() } returns ZoneId.of(alaskaTimeZone)

        val localDateTime = offsetDateTime.toSystemZonedLocalDateTime(centralTimeZone)
        assertEquals(
            LocalDateTime.of(2023, 3, 11, 9, 0, 0, 0),
            localDateTime
        )
    }

    @Test
    fun `should convert offset date time to local device zone 2`() {
        mockkStatic(ZoneId::class)
        every { ZoneId.systemDefault() } returns ZoneId.of(centralTimeZone)

        val localDateTime = offsetDateTime.toSystemZonedLocalDateTime(centralTimeZone)
        assertEquals(
            LocalDateTime.of(2023, 3, 11, 12, 0, 0, 0),
            localDateTime
        )
    }

    @Test
    fun `should convert offset date time to local device zone 3`() {
        mockkStatic(ZoneId::class)
        every { ZoneId.systemDefault() } returns ZoneId.of(tokyoTimeZone)

        val localDateTime = offsetDateTime.toSystemZonedLocalDateTime(centralTimeZone)
        assertEquals(
            LocalDateTime.of(2023, 3, 12, 3, 0, 0, 0),
            localDateTime
        )
    }

    @Test
    fun `should convert offset date time to local device zone 4`() {
        mockkStatic(ZoneId::class)
        every { ZoneId.systemDefault() } returns ZoneId.of(utcTimeZone)

        val localDateTime = offsetDateTime.toSystemZonedLocalDateTime(centralTimeZone)
        assertEquals(
            LocalDateTime.of(2023, 3, 11, 18, 0, 0, 0),
            localDateTime
        )
    }

    @Test
    fun `should show dates between two LocalDateTimes`() {
        // Whenever
        val localDateTime1 = LocalDateTime.of(2023, 3, 13, 13, 9, 0, 0)
        val localDateTime2 = LocalDateTime.of(2023, 3, 19, 0, 0, 0, 0)
        val date = LocalDate.of(2023, 3, 13)
        val expected = listOf(date, date.plusDays(1), date.plusDays(2), date.plusDays(3), date.plusDays(4), date.plusDays(5), date.plusDays(6))

        // When
        val result = localDateTime1.datesBetween(localDateTime2)
        val result2 = localDateTime2.datesBetween(localDateTime1)

        // Then
        assertEquals(expected, result)
        assertEquals(expected, result2)
    }
}
