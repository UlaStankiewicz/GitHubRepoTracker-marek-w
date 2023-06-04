package pl.mwaszczuk.githubrepotracker.reposearch.search

import androidx.compose.runtime.Immutable
import pl.mwaszczuk.githubrepotracker.core.interact.SideEffect
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem

@Immutable
data class SearchViewState(
    val isLoading: Boolean = true,
    val isNetworkError: Boolean = false,
    val errorMessage: String = "",
    val repoOwnerInput: String = "",
    val repoNameInput: String = "",
    val searchHistory: List<RepoSearchHistoryItem> = emptyList(),
    val openRepositoryDetails: SideEffect<RepoSearchHistoryItem>? = null
)
