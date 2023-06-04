package pl.mwaszczuk.githubrepotracker.design.components.topBar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

class TopBarState(
    val state: MutableState<State> = mutableStateOf(State())
) {

    fun toggleNavigationIcon(visible: Boolean) {
        state.value = state.value.copy(
            isBackArrowVisible = visible
        )
    }

    fun setTitle(title: String) {
        state.value = state.value.copy(
            title = title
        )
    }

    data class State(
        val isBackArrowVisible: Boolean = false,
        val title: String = ""
    )
}

val LocalTopBar = staticCompositionLocalOf { TopBarState() }
