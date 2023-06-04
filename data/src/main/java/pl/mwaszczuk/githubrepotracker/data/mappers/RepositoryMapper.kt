package pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.mappers

import pl.mwaszczuk.githubrepotracker.domain.model.RepositoryResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.network.response.repository.RepositoryResponse
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryWithCommits

fun RepositoryResponse.toEntity(name: String, owner: String) = RepositoryEntity(
    id = id,
    name = name,
    owner = owner
)

fun RepositoryWithCommits.toDomain() = RepositoryResource(
    id = repository.id,
    owner = repository.owner,
    name = repository.name,
    commits = commits.map { it.toDomain() }
)

fun RepositoryEntity.toDomain() = RepositorySearchItemResource(
    id = id,
    owner = owner,
    name = name
)
