package pl.mwaszczuk.githubrepotracker.design.interact

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import pl.mwaszczuk.githubrepotracker.core.interact.SideEffect

@Composable
fun <T> HandleSideEffect(
    sideEffect: SideEffect<T>?,
    onSideEffect: suspend CoroutineScope.(T) -> Unit,
) {
    LaunchedEffect(sideEffect) {
        sideEffect?.let { nonNullSideEffect ->
            nonNullSideEffect { onSideEffect(this) }
        }
    }
}
