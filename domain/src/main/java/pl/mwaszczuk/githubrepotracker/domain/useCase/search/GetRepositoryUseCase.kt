package pl.mwaszczuk.githubrepotracker.domain.useCase.search

import kotlinx.coroutines.flow.FlowCollector
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(
    private val repositoriesRepository: RepositoriesRepository
): UseCase<GetRepositoryUseCase.GetRepositoryAction>() {

    sealed interface Effect {
        object Loading : Effect
        data class Success(
            val repository: RepositorySearchItemResource
        ) : Effect

        data class Error(
            val message: String
        ) : Effect
    }

    override suspend fun FlowCollector<Any>.interact(action: GetRepositoryAction) {
        emit(Effect.Loading)
        emit(
            when (val result = repositoriesRepository.getRepository(action.owner, action.name)) {
                is DomainState.Success -> Effect.Success(result.data)
                is DomainState.Error -> Effect.Error(result.errorMessage)
            }
        )
    }

    data class GetRepositoryAction(val owner: String, val name: String) : Action
}
