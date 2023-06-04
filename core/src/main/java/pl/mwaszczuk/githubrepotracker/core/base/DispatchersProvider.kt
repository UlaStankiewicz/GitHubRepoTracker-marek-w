package pl.mwaszczuk.githubrepotracker.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pl.mwaszczuk.githubrepotracker.domain.base.DispatchersProvider

class DispatchersProviderImpl: DispatchersProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}
