package pl.mwaszczuk.githubrepotracker.reposearch.search.model

import androidx.compose.runtime.Immutable

@Immutable
data class RepoSearchHistoryItem(
    val owner: String,
    val name: String,
    val id: Int
)
