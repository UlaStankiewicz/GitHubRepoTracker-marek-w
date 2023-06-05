package pl.mwaszczuk.githubrepotracker.data

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import pl.mwaszczuk.githubrepotracker.core.test.CoroutineRule
import pl.mwaszczuk.githubrepotracker.core.test.test
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetRepositoryUseCase

class GetRepositoryUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val owner = "owner"
    private val name = "name"
    private val repository = RepositorySearchItemResource(1, owner, name)
    private val successEffect = GetRepositoryUseCase.Effect.Success(repository)

    private val repositoriesRepository: RepositoriesRepository = mockk {
        coEvery { getRepository(any(), any()) } returns DomainState.Success(repository)
    }

    private val useCase = GetRepositoryUseCase(
        repositoriesRepository = repositoriesRepository
    )

    @Test
    fun `should fetch repository and map success`() = coroutineRule.runTest {
        // When
        val result = useCase.interactOn(GetRepositoryUseCase.GetRepositoryAction(owner, name))
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetRepositoryUseCase.Effect.Loading,
                successEffect
            )
    }

    @Test
    fun `should fetch repository and map error`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoriesRepository.getRepository(any(), any()) } returns DomainState.Error("error")
        // When
        val result = useCase.interactOn(GetRepositoryUseCase.GetRepositoryAction(owner, name))
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetRepositoryUseCase.Effect.Loading,
                GetRepositoryUseCase.Effect.Error("error")
            )
    }
}
