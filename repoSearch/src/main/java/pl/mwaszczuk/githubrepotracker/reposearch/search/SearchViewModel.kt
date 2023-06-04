package pl.mwaszczuk.githubrepotracker.reposearch.search

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.mwaszczuk.githubrepotracker.core.base.BaseViewModel
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetSearchHistoryUseCase
import javax.inject.Inject

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
