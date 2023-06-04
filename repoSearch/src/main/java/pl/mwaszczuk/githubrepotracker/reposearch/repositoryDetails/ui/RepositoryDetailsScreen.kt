package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.mwaszczuk.githubrepotracker.design.interact.HandleSideEffect
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.domain.useCase.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.search.SearchViewModel
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SearchHistoryItem
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SearchTopBar

const val REPOSITORY_DETAILS_ROUTE = "repository_details"

@Composable
fun RepositoryDetailsScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    HandleSideEffect(sideEffect = state.openRepositoryDetails) {
//        navController.navigate()
    }

    val context = LocalContext.current
    HandleSideEffect(sideEffect = state.openRepositoryDetails) {
        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        RepositoryDetailsScreenLayout(
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
fun RepositoryDetailsScreenLayout(
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
fun RepositoryScreenLayoutPreview() {

}
