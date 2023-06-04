package pl.mwaszczuk.githubrepotracker.domain.useCase

import kotlinx.coroutines.flow.FlowCollector
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import javax.inject.Inject

class ChangeRepoSearchInputUseCase @Inject constructor()
    : UseCase<ChangeRepoSearchInputUseCase.ChangeRepoSearchInputAction>() {

    data class Input(
        val owner: String,
        val name: String
    )

    override suspend fun FlowCollector<Any>.interact(action: ChangeRepoSearchInputAction) {
        emit(Input(action.owner, action.name))
    }

    data class ChangeRepoSearchInputAction(val owner: String, val name: String) : Action
}
