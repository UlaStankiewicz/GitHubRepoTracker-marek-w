package pl.mwaszczuk.githubrepotracker.data

import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.CommitResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositoryResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.network.response.commit.CommitAuthorResponse
import pl.mwaszczuk.githubrepotracker.network.response.commit.CommitInfoResponse
import pl.mwaszczuk.githubrepotracker.network.response.commit.CommitResponse
import pl.mwaszczuk.githubrepotracker.network.response.repository.RepositoryResponse
import pl.mwaszczuk.githubrepotracker.persistence.model.CommitEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryWithCommits
import retrofit2.Response

const val repoOwner = "owner"
const val repoName = "name"

val repositoryResponse: Response<RepositoryResponse> = Response.success(
    RepositoryResponse(
        id = 1
    )
)

val databaseRepository = RepositoryEntity(
    id = 1,
    name = repoName,
    owner = repoOwner
)

val searchHistoryDatabase = listOf(
    RepositoryEntity(
        id = 1,
        owner = "owner1",
        name = "name1"
    ),
    RepositoryEntity(
        id = 2,
        owner = "owner2",
        name = "name2"
    ),
    RepositoryEntity(
        id = 3,
        owner = "owner3",
        name = "name3"
    )
)

val repositoryMapped = RepositorySearchItemResource(
    id = 1,
    owner = repoOwner,
    name = repoName
)

val searchHistoryMapped = DomainState.Success(
    listOf(
        RepositorySearchItemResource(
            id = 1,
            owner = "owner1",
            name = "name1"
        ),
        RepositorySearchItemResource(
            id = 2,
            owner = "owner2",
            name = "name2"
        ),
        RepositorySearchItemResource(
            id = 3,
            owner = "owner3",
            name = "name3"
        )
    )
)

val commitsDatabase = listOf(
    CommitEntity(
        sha = "sha1",
        message = "message1",
        authorName = "authorName1",
        repositoryId = 1
    ),
    CommitEntity(
        sha = "sha2",
        message = "message2",
        authorName = "authorName2",
        repositoryId = 1
    )
)

val commitsResponse = listOf(
    CommitResponse(
        sha = "sha1",
        commitInfo = CommitInfoResponse(
            message = "message1",
            author = CommitAuthorResponse(
                name = "authorName1"
            )
        ),
    ),
    CommitResponse(
        sha = "sha2",
        commitInfo = CommitInfoResponse(
            message = "message2",
            author = CommitAuthorResponse(
                name = "authorName2"
            )
        ),
    )
)

val commitsResource = listOf(
    CommitResource(
        sha = "sha1",
        message = "message1",
        authorName = "authorName1"
    ),
    CommitResource(
        sha = "sha2",
        message = "message2",
        authorName = "authorName2"
    )
)

val repoWithCommits = RepositoryWithCommits(
    repository = databaseRepository,
    commits = commitsDatabase
)

val repoWithCommitsResource = RepositoryResource(
    id = 1,
    owner = repoOwner,
    name = repoName,
    commits = commitsResource
)
