package pl.mwaszczuk.githubrepotracker.data.repository

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import pl.mwaszczuk.githubrepotracker.domain.base.DispatchersProvider
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.data.ResponseMapper
import pl.mwaszczuk.githubrepotracker.network.api.RepositoriesApi
import pl.mwaszczuk.githubrepotracker.data.mappers.toDomain
import pl.mwaszczuk.githubrepotracker.data.mappers.toEntity
import pl.mwaszczuk.githubrepotracker.persistence.dao.RepositoryDao

class RepositoriesRepositoryImpl(
    private val repositoryApi: RepositoriesApi,
    private val repositoryDao: RepositoryDao,
    private val responseMapper: ResponseMapper,
    private val dispatchers: DispatchersProvider
) : RepositoriesRepository() {

    override suspend fun getSearchHistory() = withContext(dispatchers.io) {
        repositoryDao.getSearchHistory()
            .map { list -> domainStateOf { list.map { it.toDomain() } } }
    }

    override suspend fun getRepository(owner: String, name: String) = withContext(dispatchers.io) {
        domainStateOf {
            repositoryDao.getRepository(owner, name)?.toDomain() ?: responseMapper
                .map(repositoryApi.getRepository(owner, name))
                .toEntity(name, owner).let {
                    repositoryDao.insertRepository(it)
                    it.toDomain()
                }
        }
    }

    private suspend fun updateCommits(owner: String, repo: String, repoId: Int) =
        withContext(dispatchers.io) {
            val response = responseMapper.map(repositoryApi.getCommits(owner, repo))
            repositoryDao.insertCommits(response.map { it.toEntity(repoId) })
        }

    override suspend fun getCommits(
        repoOwner: String,
        repoName: String,
        repoId: Int
    ) = repositoryDao.getRepositoryWithCommits(repoId)
        .map { domainStateOf { it.toDomain() } }
        .onCompletion {
            val networkUpdateState =
                domainStateOf { updateCommits(repoOwner, repoName, repoId) }
            if (networkUpdateState is DomainState.Error) emit(networkUpdateState)
        }
        .flowOn(dispatchers.io)
}
