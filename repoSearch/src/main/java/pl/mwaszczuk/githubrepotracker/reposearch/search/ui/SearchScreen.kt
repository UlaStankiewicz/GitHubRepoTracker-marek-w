package pl.mwaszczuk.githubrepotracker.reposearch.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.mwaszczuk.githubrepotracker.design.interact.HandleSideEffect
import pl.mwaszczuk.githubrepotracker.design.localComposition.LocalNavController
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.domain.useCase.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.REPOSITORY_DETAILS_ROUTE
import pl.mwaszczuk.githubrepotracker.reposearch.search.SearchViewModel
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem

const val SEARCH_SCREEN_ROUTE = "search"

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    HandleSideEffect(sideEffect = state.openRepositoryDetails) {
        navController?.navigate(REPOSITORY_DETAILS_ROUTE + "/${it.owner}/${it.name}/${it.id}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SearchScreenLayout(
            history = state.searchHistory,
            searchRepoOwnerInput = state.repoOwnerInput,
            searchRepoNameInput = state.repoNameInput,
            onItemClicked = {
                viewModel.publish(
                    GetRepositoryUseCase.GetRepositoryAction(
                        it.owner,
                        it.name
                    )
                )
            },
            onSearchInputChanged = { owner, name ->
                viewModel.publish(
                    ChangeRepoSearchInputUseCase.ChangeRepoSearchInputAction(owner, name)
                )
            },
            onSearchClicked = { owner, name ->
                viewModel.publish(
                    GetRepositoryUseCase.GetRepositoryAction(
                        owner,
                        name
                    )
                )
            }
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopCenter),
            visible = state.isLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = SizeS)
                    .shadow(elevation = SizeXXXS, shape = CircleShape)
                    .background(MaterialTheme.colors.surface, CircleShape)
            )
        }
    }
}

@Composable
fun SearchScreenLayout(
    history: List<RepoSearchHistoryItem>,
    searchRepoOwnerInput: String,
    searchRepoNameInput: String,
    onItemClicked: (RepoSearchHistoryItem) -> Unit,
    onSearchInputChanged: (String, String) -> Unit,
    onSearchClicked: (String, String) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                repoOwnerInput = searchRepoOwnerInput,
                repoNameInput = searchRepoNameInput,
                onSearchInputChanged = onSearchInputChanged,
                onSearchClicked = onSearchClicked
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = SizeS),
            contentPadding = PaddingValues(bottom = SizeS, start = SizeS, end = SizeS),
            verticalArrangement = Arrangement.spacedBy(SizeS)
        ) {
            items(history) { item ->
                SearchHistoryItem(item, onItemClicked)
            }
        }
    }
}

@Preview
@Composable
fun SearchLayoutPreview() {
    SearchScreenLayout(
        history = listOf(
            RepoSearchHistoryItem(owner = "mwaszczuk", name = "githubrepotracker", id = 1)
        ),
        searchRepoOwnerInput = "mwaszczuk",
        searchRepoNameInput = "githubrepotracker",
        onItemClicked = {},
        onSearchInputChanged = { _, _ -> },
        onSearchClicked = { _, _ -> }
    )
}
