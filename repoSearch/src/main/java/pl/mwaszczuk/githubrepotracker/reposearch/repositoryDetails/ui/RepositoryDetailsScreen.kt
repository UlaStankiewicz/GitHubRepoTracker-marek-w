package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.mwaszczuk.githubrepotracker.design.components.TopBar
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.reposearch.R
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.RepositoryDetailsViewModel
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

const val REPOSITORY_DETAILS_ROUTE = "repository_details"

@Composable
fun RepositoryDetailsScreen(
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        RepositoryDetailsScreenLayout(
            commits = state.commits,
            repoOwner = state.repoOwner,
            repoName = state.repoName,
            onItemClicked = {

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
    commits: List<Commit>,
    repoName: String,
    repoOwner: String,
    onItemClicked: (Commit) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    R.string.repository_details_repo_name, repoOwner, repoName)
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
            items(commits) { item ->
                CommitListItem(item, onItemClicked)
            }
        }
    }
}

@Preview
@Composable
fun RepositoryScreenLayoutPreview() {
    RepositoryDetailsScreenLayout(
        commits = listOf(
            Commit("sha", "message", "authorName")
        ),
        repoName = "repo",
        repoOwner = "owner",
        onItemClicked = { }
    )
}
