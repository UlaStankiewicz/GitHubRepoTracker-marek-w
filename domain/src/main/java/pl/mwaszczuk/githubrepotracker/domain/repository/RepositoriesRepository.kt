package pl.mwaszczuk.githubrepotracker.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.RepositoryResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.domain.base.BaseRepository as BaseRepository

abstract class RepositoriesRepository : BaseRepository() {

    abstract suspend fun getSearchHistory(): Flow<DomainState<List<RepositorySearchItemResource>>>
    abstract suspend fun getRepository(owner: String, name: String): DomainState<RepositorySearchItemResource>
    abstract suspend fun getCommits(
        repoOwner: String,
        repoName: String,
        repoId: Int
    ): Flow<DomainState<RepositoryResource>>
}
