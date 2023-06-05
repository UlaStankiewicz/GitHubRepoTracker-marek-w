package pl.mwaszczuk.githubrepotracker.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import pl.mwaszczuk.githubrepotracker.core.test.CoroutineRule
import pl.mwaszczuk.githubrepotracker.core.test.test
import pl.mwaszczuk.githubrepotracker.domain.base.ApiException
import pl.mwaszczuk.githubrepotracker.domain.base.DispatchersProvider
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.network.api.RepositoriesApi
import pl.mwaszczuk.githubrepotracker.data.repository.RepositoriesRepositoryImpl
import pl.mwaszczuk.githubrepotracker.persistence.dao.RepositoryDao
import retrofit2.Response

class RepositoriesRepositoryImplTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val repositoryApi: RepositoriesApi = mockk {
        coEvery { getRepository(any(), any()) } returns repositoryResponse
        coEvery { getCommits(any(), any()) } returns Response.success(commitsResponse)
    }
    private val repositoryDao: RepositoryDao = mockk(relaxed = true) {
        every { getSearchHistory() } returns flowOf(searchHistoryDatabase)
        every { getRepositoryWithCommits(any()) } returns flowOf(repoWithCommits)
    }
    private val dispatchers: DispatchersProvider = mockk {
        every { io } returns coroutineRule.dispatcher
    }

    private val repository = RepositoriesRepositoryImpl(
        repositoryApi = repositoryApi,
        repositoryDao = repositoryDao,
        responseMapper = ResponseMapper(),
        dispatchers = dispatchers
    )

    @Test
    fun `should get and properly map search history from database`() = coroutineRule.runTest {
        // When
        val result = repository.getSearchHistory()
        // Then
        verify(exactly = 1) { repositoryDao.getSearchHistory() }
        result
            .test(this)
            .assertValuesAndFinish(searchHistoryMapped)
    }

    @Test
    fun `should get and properly map repository and insert to search history database`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoryDao.getRepository(any(), any()) } returns null
        // When
        val result = repository.getRepository(repoOwner, repoName)
        // Then
        coVerifyOrder {
            repositoryDao.getRepository(repoOwner, repoName)
            repositoryDao.insertRepository(databaseRepository)
        }
        assertEquals(DomainState.Success(repositoryMapped), result)
    }

    @Test
    fun `should get and properly map repository only from database if exists`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoryDao.getRepository(any(), any()) } returns databaseRepository
        // When
        val result = repository.getRepository(repoOwner, repoName)
        // Then
        coVerify(exactly = 1) {
            repositoryDao.getRepository(repoOwner, repoName)
        }
        assertEquals(result, DomainState.Success(repositoryMapped))
    }

    @Test
    fun `should get and and save commits to database`() = coroutineRule.runTest {
        // When
        val result = repository.getCommits(repoOwner, repoName, 1)
        // Then
        result
            .test(this)
            .assertValuesAndFinish(DomainState.Success(repoWithCommitsResource))
        coVerifyOrder {
            repositoryDao.getRepositoryWithCommits(1)
            repositoryApi.getCommits(repoOwner, repoName)
            repositoryDao.insertCommits(commitsDatabase)
        }
    }

    @Test
    fun `should emit error on commits Api update error`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoryApi.getCommits(any(), any()) } throws ApiException.NotFound()
        // When
        val result = repository.getCommits(repoOwner, repoName, 1)
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                DomainState.Success(repoWithCommitsResource),
                DomainState.Error.ApiError(ApiException.NotFound().message)
            )


        coVerifyOrder {
            repositoryDao.getRepositoryWithCommits(1)
            repositoryApi.getCommits(repoOwner, repoName)
        }
    }
}
