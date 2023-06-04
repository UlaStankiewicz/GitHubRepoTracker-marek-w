package pl.mwaszczuk.githubrepotracker.core.interact

import org.jetbrains.annotations.TestOnly

class SideEffect<EFFECT>(private val sideEffect: EFFECT?) {

    private var isConsumed = false

    suspend operator fun invoke(block: suspend EFFECT.() -> Unit) {
        if (!isConsumed) {
            sideEffect?.block()
            isConsumed = true
        }
    }

    @TestOnly
    fun getSideEffect() = sideEffect
}
