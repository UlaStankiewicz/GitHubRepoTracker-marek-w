package pl.mwaszczuk.githubrepotracker.reposearch

import org.junit.Test
import pl.mwaszczuk.githubrepotracker.domain.model.CommitResource
import pl.mwaszczuk.githubrepotracker.domain.model.RepositoryResource
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.CloseSelectModeUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.SelectCommitUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ShareCommitsUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.RepositoryDetailsViewState
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.closeSelectionModeReducer
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.commitSelectionReducer
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.commitsReducer
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.mapper.toUi
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.shareCommitsReducer
import kotlin.test.assertEquals

class RepositoryDetailsReducersTest {

    private val state = RepositoryDetailsViewState()

    private val repoId = 1
    private val repoOwner = "owner"
    private val repoName = "name"
    private val sha = "sha"
    private val commit = CommitResource(sha, "message", "author")

    private val repository = RepositoryResource(
        id = repoId,
        owner = repoOwner,
        name = repoName,
        commits = listOf(commit)
    )

    private val successState = state.copy(
        isLoading = false,
        repoOwner = repoOwner,
        repoName = repoName,
        repoId = repoId,
        commits = listOf(commit).map { it.toUi() }
    )

    @Test
    fun `should react to successfully fetched commits`() {
        // When
        val targetState = commitsReducer(
            state,
            GetCommitsUseCase.Effect.Success(repository)
        )
        // Then
        assertEquals(
            successState,
            targetState
        )
    }

    @Test
    fun `should react to commit fetch error`() {
        // When
        val targetState = commitsReducer(
            state,
            GetCommitsUseCase.Effect.Error("error")
        )
        // Then
        assertEquals(
            state.copy(
                isLoading = false,
                commitsFetchError = "error"
            ),
            targetState
        )
    }

    @Test
    fun `should select commits`() {
        // When
        val commitState = commitsReducer(
            state,
            GetCommitsUseCase.Effect.Success(repository)
        )
        val targetState = commitSelectionReducer(
            commitState,
            SelectCommitUseCase.CommitSelectionEffect(sha, true)
        )
        // Then
        assertEquals(
            successState.copy(
                isLoading = false,
                isSelectModeOn = true,
                commits = listOf(commit.toUi().copy(isSelected = true))
            ),
            targetState
        )
    }

    @Test
    fun `should close selection mode`() {
        // When
        val commitState = commitsReducer(
            state,
            GetCommitsUseCase.Effect.Success(repository)
        )
        val selectedCommitState = commitSelectionReducer(
            commitState,
            SelectCommitUseCase.CommitSelectionEffect(sha, true)
        )
        val targetState = closeSelectionModeReducer(
            selectedCommitState,
            CloseSelectModeUseCase.CloseSelectModeAction
        )
        // Then
        assertEquals(
            successState.copy(
                isSelectModeOn = false,
                commits = listOf(commit.toUi())
            ),
            targetState
        )
    }

    @Test
    fun `should share commits`() {
        // When
        val commitState = commitsReducer(
            state,
            GetCommitsUseCase.Effect.Success(repository)
        )
        val selectedCommitState = commitSelectionReducer(
            commitState,
            SelectCommitUseCase.CommitSelectionEffect(sha, true)
        )
        val targetState = shareCommitsReducer(
            selectedCommitState,
            ShareCommitsUseCase.ShareCommitsAction
        )
        // Then
        assertEquals(
            listOf(commit.toUi()),
            targetState.shareCommits?.getSideEffect()?.toList()
        )
    }
}
