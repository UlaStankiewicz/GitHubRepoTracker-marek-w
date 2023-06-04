package pl.mwaszczuk.githubrepotracker.reposearch.search

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.mwaszczuk.githubrepotracker.core.base.BaseViewModel
import pl.mwaszczuk.githubrepotracker.core.interact.SideEffect
import pl.mwaszczuk.githubrepotracker.core.interact.reducer
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetSearchHistoryUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.search.mapper.toUI
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem
import javax.inject.Inject

val searchHistoryReducer = reducer<SearchViewState, GetSearchHistoryUseCase.Effect> {
    when (it) {
        is GetSearchHistoryUseCase.Effect.Loading -> copy(isLoading = true)
        is GetSearchHistoryUseCase.Effect.Error -> copy (isLoading = false)
        is GetSearchHistoryUseCase.Effect.Success -> copy(
            isLoading = false,
            searchHistory = it.searchItems.map { searchItem -> searchItem.toUI() }
        )
    }
}

val searchInputReducer = reducer<SearchViewState, ChangeRepoSearchInputUseCase.Input> {
    copy(repoOwnerInput = it.owner, repoNameInput = it.name)
}

val getRepositoryReducer = reducer<SearchViewState, GetRepositoryUseCase.Effect> {
    when (it) {
        is GetRepositoryUseCase.Effect.Loading -> copy(isLoading = true)
        is GetRepositoryUseCase.Effect.Error -> copy(isLoading = false)
        is GetRepositoryUseCase.Effect.Success -> copy(
            isLoading = false,
            openRepositoryDetails = SideEffect(RepoSearchHistoryItem(it.repository.owner, it.repository.name, it.repository.id))
        )
    }
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    getSearchHistoryUseCase: GetSearchHistoryUseCase,
    getRepositoryUseCase: GetRepositoryUseCase,
    changeRepoSearchInputUseCase: ChangeRepoSearchInputUseCase
) : BaseViewModel<SearchViewState>(
    initialState = SearchViewState(),
    useCases = listOf(getSearchHistoryUseCase, getRepositoryUseCase, changeRepoSearchInputUseCase),
    reducers = listOf(searchHistoryReducer, searchInputReducer, getRepositoryReducer),
) {

    init {
        publish(GetSearchHistoryUseCase.GetSearchHistoryAction)
    }
}
