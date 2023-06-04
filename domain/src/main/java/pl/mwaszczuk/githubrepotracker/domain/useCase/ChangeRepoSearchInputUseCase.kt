package pl.mwaszczuk.githubrepotracker.domain.useCase

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import pl.mwaszczuk.githubrepotracker.domain.model.Repository
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItem
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
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
