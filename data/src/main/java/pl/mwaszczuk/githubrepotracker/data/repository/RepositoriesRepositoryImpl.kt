package pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.repository

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.Repository
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.network.ResponseMapper
import pl.mwaszczuk.githubrepotracker.network.api.RepositoriesApi
import pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.mappers.toDomain
import pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.mappers.toEntity
import pl.mwaszczuk.githubrepotracker.persistence.dao.RepositoryDao

class RepositoriesRepositoryImpl(
    private val repositoryApi: RepositoriesApi,
    private val repositoryDao: RepositoryDao,
    private val responseMapper: ResponseMapper
) : RepositoriesRepository() {

    override suspend fun getSearchHistory() = domainStateOf {
        repositoryDao.getSearchHistory().map { it.toDomain() }
    }

    override suspend fun getRepository(owner: String, name: String) = domainStateOf {
        repositoryDao.getRepository(owner, name)?.toDomain() ?: responseMapper
            .map(repositoryApi.getRepository(owner, name))
            .toEntity(name, owner).let {
                repositoryDao.insertRepository(it)
                it.toDomain()
            }
    }

    private suspend fun updateCommits(owner: String, repo: String, repoId: Int) {
        val response = responseMapper.map(repositoryApi.getCommits(owner, repo))
        repositoryDao.insertCommits(response.map { it.toEntity(repoId) })
    }

    override suspend fun getCommits(repo: Repository) = repositoryDao.getRepositoryWithCommits(repo.id)
            .map { domainStateOf { it.toDomain() } }
            .onStart {
                val networkUpdateState =
                    domainStateOf { updateCommits(repo.owner, repo.name, repo.id) }
                if (networkUpdateState is DomainState.Error) emit(networkUpdateState)
            }
}
