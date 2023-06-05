package pl.mwaszczuk.githubrepotracker.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import pl.mwaszczuk.githubrepotracker.core.test.CoroutineRule
import pl.mwaszczuk.githubrepotracker.core.test.test
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetSearchHistoryUseCase

class GetSearchHistoryUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val owner = "owner"
    private val name = "name"
    private val repository = RepositorySearchItemResource(1, owner, name)
    private val repository2 = RepositorySearchItemResource(2, owner, "name2")
    private val searchHistory = listOf(repository, repository2)
    private val successEffect = GetSearchHistoryUseCase.Effect.Success(searchHistory)

    private val repositoriesRepository: RepositoriesRepository = mockk {
        coEvery { getSearchHistory() } returns flowOf(DomainState.Success(searchHistory))
    }

    private val useCase = GetSearchHistoryUseCase(
        repositoriesRepository = repositoriesRepository
    )

    @Test
    fun `should fetch search history and map success`() = coroutineRule.runTest {
        // When
        val result = useCase.interactOn(GetSearchHistoryUseCase.GetSearchHistoryAction)
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetSearchHistoryUseCase.Effect.Loading,
                successEffect
            )
    }

    @Test
    fun `should fetch search history and map error`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoriesRepository.getSearchHistory() } returns flowOf(DomainState.Error("error"))
        // When
        val result = useCase.interactOn(GetSearchHistoryUseCase.GetSearchHistoryAction)
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetSearchHistoryUseCase.Effect.Loading,
                GetSearchHistoryUseCase.Effect.Error("error")
            )
    }
}
