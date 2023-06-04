package pl.mwaszczuk.githubrepotracker.core.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineRule(
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    private val coroutineScope: TestScope = TestScope(dispatcher)

    override fun apply(base: Statement, description: Description) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(dispatcher)
            base.evaluate()
            Dispatchers.resetMain()
        }
    }

    fun runTest(block: suspend TestScope.() -> Unit) =
        coroutineScope.runTest {
            block()
            advanceUntilIdle()// run the remaining tasks
        }
}
