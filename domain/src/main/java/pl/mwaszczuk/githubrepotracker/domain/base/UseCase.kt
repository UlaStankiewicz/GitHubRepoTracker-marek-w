package pl.mwaszczuk.githubrepotracker.domain.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class UseCase<T : Action> {

    private val type: Type =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]

    suspend fun interactOn(action: T): Flow<Any> = flow { interact(action) }

    open fun filter(actions: Flow<*>): Flow<T> =
        (actions.filter { it != null && it::class.java == type } as Flow<T>)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun interactWith(actions: Flow<*>): Flow<Any> = filter(actions).flatMapLatest(::interactOn)

    abstract suspend fun FlowCollector<Any>.interact(action: T)
}
