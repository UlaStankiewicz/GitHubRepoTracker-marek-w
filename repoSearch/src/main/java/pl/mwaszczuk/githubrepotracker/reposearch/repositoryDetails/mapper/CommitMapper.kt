package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.mapper

import pl.mwaszczuk.githubrepotracker.domain.model.CommitResource
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

fun CommitResource.toUi() = Commit(
    sha = sha,
    message = message,
    authorName = authorName
)
