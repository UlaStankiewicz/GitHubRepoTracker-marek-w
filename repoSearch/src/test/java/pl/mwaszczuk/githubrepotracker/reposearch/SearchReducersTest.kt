package pl.mwaszczuk.githubrepotracker.reposearch

import org.junit.Test
import pl.mwaszczuk.githubrepotracker.domain.model.RepositorySearchItemResource
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetSearchHistoryUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.search.SearchViewState
import pl.mwaszczuk.githubrepotracker.reposearch.search.getRepositoryReducer
import pl.mwaszczuk.githubrepotracker.reposearch.search.mapper.toUI
import pl.mwaszczuk.githubrepotracker.reposearch.search.searchHistoryReducer
import pl.mwaszczuk.githubrepotracker.reposearch.search.searchInputReducer
import kotlin.test.assertEquals

class SearchReducersTest {

    private val state = SearchViewState()

    private val repoId = 1
    private val repoOwner = "owner"
    private val repoName = "name"

    private val repository = RepositorySearchItemResource(repoId, repoOwner, repoName)

    @Test
    fun `should react to fetched repository effect`() {
        // When
        val targetState = getRepositoryReducer(
            state,
            GetRepositoryUseCase.Effect.Success(repository)
        )
        // Then
        assertEquals(false, targetState.isLoading)
        assertEquals(
            repository.toUI(),
            targetState.openRepositoryDetails?.getSideEffect()
        )
    }

    @Test
    fun `should react to repository fetch error effect`() {
        // When
        val targetState = getRepositoryReducer(
            state,
            GetRepositoryUseCase.Effect.Error("error")
        )
        // Then
        assertEquals(false, targetState.isLoading)
        assertEquals(
            "error",
            targetState.showErrorMessage?.getSideEffect()
        )
    }

    @Test
    fun `should react to search history get success`() {
        // When
        val targetState = searchHistoryReducer(
            state,
            GetSearchHistoryUseCase.Effect.Success(listOf(repository))
        )
        // Then
        assertEquals(
            state.copy(
                isLoading = false,
                searchHistory = listOf(repository.toUI())
            ),
            targetState
        )
    }

    @Test
    fun `should change search input`() {
        // When
        val targetState = searchInputReducer(
            state,
            ChangeRepoSearchInputUseCase.Input("owner3", "name3")
        )
        // Then
        assertEquals(
            state.copy(
                repoOwnerInput = "owner3",
                repoNameInput = "name3"
            ),
            targetState
        )
    }
}
