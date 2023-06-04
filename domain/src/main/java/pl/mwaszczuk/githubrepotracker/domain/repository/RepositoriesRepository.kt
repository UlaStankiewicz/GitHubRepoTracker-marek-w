package pl.mwaszczuk.githubrepotracker.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.Repository
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItem
import pl.mwaszczuk.githubrepotracker.domain.base.BaseRepository as BaseRepository

abstract class RepositoriesRepository : BaseRepository() {

    abstract suspend fun getSearchHistory(): DomainState<List<RepositorySearchItem>>
    abstract suspend fun getRepository(owner: String, name: String): DomainState<RepositorySearchItem>
    abstract suspend fun getCommits(repo: Repository): Flow<DomainState<Repository>>
}
