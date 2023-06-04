package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.components.TopBar
import pl.mwaszczuk.githubrepotracker.design.interact.HandleSideEffect
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.CloseSelectModeUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.SelectCommitUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ShareCommitsUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.R
import pl.mwaszczuk.githubrepotracker.reposearch.extensions.shareCommits
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.RepositoryDetailsViewModel
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

const val REPOSITORY_DETAILS_ROUTE = "repository_details"

@Composable
fun RepositoryDetailsScreen(
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    HandleSideEffect(sideEffect = state.shareCommits) {
        context.shareCommits(it, state.repoName, state.repoOwner)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        RepositoryDetailsScreenLayout(
            commits = state.commits,
            isSelectModeOn = state.isSelectModeOn,
            errorMessage = state.commitsFetchError,
            repoOwner = state.repoOwner,
            repoName = state.repoName,
            onItemSelected = {
                viewModel.publish(SelectCommitUseCase.SelectCommitAction(it.sha, it.isSelected))
            },
            onShareClicked = {
                viewModel.publish(ShareCommitsUseCase.ShareCommitsAction)
            },
            onCloseSelectMode = {
                viewModel.publish(CloseSelectModeUseCase.CloseSelectModeAction)
            },
            onRetry = {
                viewModel.publish(GetCommitsUseCase.GetCommitsAction(
                    state.repoOwner, state.repoName, state.repoId
                ))
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
    isSelectModeOn: Boolean,
    errorMessage: String?,
    repoName: String,
    repoOwner: String,
    onItemSelected: (Commit) -> Unit,
    onShareClicked: () -> Unit,
    onCloseSelectMode: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    R.string.repository_details_repo_name, repoOwner, repoName
                ),
                contentRight = {
                    if (isSelectModeOn) {
                        TopBarSelectModeIcons(
                            onShareClicked = onShareClicked,
                            onCloseClicked = onCloseSelectMode
                        )
                    }
                }
            )
        }
    ) {
        Column {
            AnimatedVisibility(visible = errorMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.error)
                        .padding(horizontal = SizeS),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = errorMessage ?: "",
                        style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onBackground)
                    )
                    Button(
                        onClick = onRetry
                    ) {
                        Text(text = "retry")
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = SizeS),
                contentPadding = PaddingValues(bottom = SizeS, start = SizeS, end = SizeS),
                verticalArrangement = Arrangement.spacedBy(SizeS)
            ) {
                items(commits) { item ->
                    CommitListItem(
                        item = item,
                        isSelectModeOn = isSelectModeOn,
                        onSelected = onItemSelected
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarSelectModeIcons(
    onShareClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        Icon(
            modifier = Modifier
                .padding(end = SizeXS)
                .clickable {
                    onShareClicked()
                },
            painter = painterResource(
                id = DesignDrawables.ic_share
            ),
            contentDescription = "share"
        )

        Icon(
            modifier = Modifier
                .clickable {
                    onCloseClicked()
                },
            painter = painterResource(
                id = DesignDrawables.ic_close
            ),
            contentDescription = "close"
        )
    }
}

@Preview
@Composable
fun RepositoryScreenLayoutPreview() {
    RepositoryDetailsScreenLayout(
        commits = listOf(
            Commit("sha", "message", "authorName")
        ),
        isSelectModeOn = false,
        errorMessage = null,
        repoName = "repo",
        repoOwner = "owner",
        onItemSelected = { },
        onShareClicked = { },
        onCloseSelectMode = { },
        onRetry = { }
    )
}
