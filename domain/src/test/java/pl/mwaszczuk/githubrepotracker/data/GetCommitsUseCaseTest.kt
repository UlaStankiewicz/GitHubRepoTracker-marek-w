package pl.mwaszczuk.githubrepotracker.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import pl.mwaszczuk.githubrepotracker.core.test.CoroutineRule
import pl.mwaszczuk.githubrepotracker.core.test.test
import pl.mwaszczuk.githubrepotracker.domain.base.DomainState
import pl.mwaszczuk.githubrepotracker.domain.model.CommitResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositoryResource
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase

class GetCommitsUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val owner = "owner"
    private val name = "name"
    private val repoId = 1
    private val commits = listOf(
        CommitResource("sha", "message", "authorName")
    )
    private val repository = RepositoryResource(
        id = 1,
        owner = owner,
        name = name,
        commits = commits
    )
    private val successEffect = GetCommitsUseCase.Effect.Success(repository)

    private val repositoriesRepository: RepositoriesRepository = mockk {
        coEvery { getCommits(any(), any(), any()) } returns flowOf(DomainState.Success(repository))
    }

    private val useCase = GetCommitsUseCase(
        repositoriesRepository = repositoriesRepository
    )

    @Test
    fun `should fetch repository and map success`() = coroutineRule.runTest {
        // When
        val result = useCase.interactOn(GetCommitsUseCase.GetCommitsAction(owner, name, repoId))
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetCommitsUseCase.Effect.Loading,
                successEffect
            )
    }

    @Test
    fun `should fetch repository and map error`() = coroutineRule.runTest {
        // Whenever
        coEvery { repositoriesRepository.getCommits(any(), any(), any()) } returns flowOf(DomainState.Error("error"))
        // When
        val result = useCase.interactOn(GetCommitsUseCase.GetCommitsAction(owner, name, repoId))
        // Then
        result
            .test(this)
            .assertValuesAndFinish(
                GetCommitsUseCase.Effect.Loading,
                GetCommitsUseCase.Effect.Error("error")
            )
    }
}
