package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.mwaszczuk.githubrepotracker.core.base.BaseViewModel
import pl.mwaszczuk.githubrepotracker.core.interact.reducer
import pl.mwaszczuk.githubrepotracker.domain.useCase.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.mapper.toUi
import javax.inject.Inject

val commitsReducer = reducer<RepositoryDetailsViewState, GetCommitsUseCase.Effect> {
    when (it) {
        is GetCommitsUseCase.Effect.Loading -> copy(isLoading = true)
        is GetCommitsUseCase.Effect.Error -> copy (isLoading = false)
        is GetCommitsUseCase.Effect.Success -> copy(
            isLoading = false,
            repoOwner = it.repository.owner,
            repoName = it.repository.name,
            commits = it.repository.commits.map { commit -> commit.toUi() }
        )
    }
}

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    getCommitsUseCase: GetCommitsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RepositoryDetailsViewState>(
    initialState = RepositoryDetailsViewState(),
    useCases = listOf(getCommitsUseCase),
    reducers = listOf(commitsReducer),
) {

    init {
        val owner = checkNotNull(savedStateHandle.get<String>("repoOwner"))
        val name = checkNotNull(savedStateHandle.get<String>("repoName"))
        val id = checkNotNull(savedStateHandle.get<Int>("repoId"))
        publish(GetCommitsUseCase.GetCommitsAction(owner, name, id))
    }
}
