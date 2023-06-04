package pl.mwaszczuk.githubrepotracker.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase

abstract class BaseViewModel<STATE>(
    initialState: STATE,
    useCases: List<UseCase<*>>,
    reducers: List<(STATE, Any) -> STATE>,
) : ViewModel() {

    private val actions = MutableSharedFlow<Action>()
    private val effects = useCases.map { it.interactWith(actions) }
        .merge()
        .shareIn(viewModelScope, SharingStarted.Eagerly, Int.MAX_VALUE)

    val state = effects.scan(initialState) { state, effect ->
        reducers.fold(state) { accState, reduce ->
            reduce(
                accState,
                effect
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    fun publish(action: Action) {
        viewModelScope.launch {
            actions.emit(action)
        }
    }
}
