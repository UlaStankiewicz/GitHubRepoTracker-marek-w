package pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.mappers

import pl.mwaszczuk.githubrepotracker.domain.model.CommitResource
import pl.mwaszczuk.githubrepotracker.network.response.commit.CommitResponse
import pl.mwaszczuk.githubrepotracker.persistence.model.CommitEntity

fun CommitResponse.toEntity(repoId: Int) = CommitEntity(
    sha = sha,
    message = commitInfo.message,
    authorName = commitInfo.author.name,
    repositoryId = repoId
)

fun CommitEntity.toDomain() = CommitResource(
    sha = sha,
    message = message,
    authorName = authorName
)

fun CommitResponse.toDomain() = CommitResource(
    sha = sha,
    message = commitInfo.message,
    authorName = commitInfo.author.name
)
