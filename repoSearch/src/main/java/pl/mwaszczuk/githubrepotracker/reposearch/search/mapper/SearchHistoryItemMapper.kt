package pl.mwaszczuk.githubrepotracker.reposearch.search.mapper

import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem

fun RepositorySearchItemResource.toUI() = RepoSearchHistoryItem(
    owner = owner,
    name = name,
    id = id
)
