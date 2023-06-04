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

class GetCommitsUseCase @Inject constructor(
    private val repositoriesRepository: RepositoriesRepository
) : UseCase<GetCommitsUseCase.GetCommitsAction>() {

    sealed interface Effect {
        object Loading : Effect
        data class Success(
            val repository: Repository
        ) : Effect

        data class Error(
            val message: String
        ) : Effect
    }

    override suspend fun FlowCollector<Any>.interact(action: GetCommitsAction) {
        emit(Effect.Loading)
        emitAll(
            repositoriesRepository.getCommits(action.repo)
                .map {
                    when (it) {
                        is DomainState.Success -> Effect.Success(it.data)
                        is DomainState.Error -> Effect.Error(it.errorMessage)
                    }
                }
        )
    }

    data class GetCommitsAction(val repo: Repository) : Action
}
