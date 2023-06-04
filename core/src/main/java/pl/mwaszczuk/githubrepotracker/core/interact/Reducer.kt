package pl.mwaszczuk.githubrepotracker.core.interact

inline fun <State, reified Effect> reducer(
    crossinline reduce: State.(Effect) -> State,
): (State, Any) -> State =
    { state, effect -> if (effect is Effect) state.reduce(effect) else state }
