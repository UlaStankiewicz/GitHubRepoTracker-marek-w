package pl.mwaszczuk.githubrepotracker.core

import pl.mwaszczuk.githubrepotracker.core.formatter.capitalizeLowerCase
import org.junit.Test
import kotlin.test.assertEquals

class StringFormatterTest {

    private val string1 = "MONDAY"
    private val string2 = "DAY ShIfT"

    private val expectedString1Result = "Monday"
    private val expectedString2Result = "Day shift"

    @Test
    fun `should lowercase and capitalize`() {
        assertEquals(string1.capitalizeLowerCase(), expectedString1Result)
        assertEquals(string2.capitalizeLowerCase(), expectedString2Result)
    }
}
