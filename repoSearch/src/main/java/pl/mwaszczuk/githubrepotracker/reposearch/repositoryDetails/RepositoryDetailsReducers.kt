package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails

import pl.mwaszczuk.githubrepotracker.core.interact.SideEffect
import pl.mwaszczuk.githubrepotracker.core.interact.reducer
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ChangeRepoSearchInputUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.CloseSelectModeUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.SelectCommitUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ShareCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetRepositoryUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.search.GetSearchHistoryUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.mapper.toUi
import pl.mwaszczuk.githubrepotracker.reposearch.search.mapper.toUI
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem

val commitsReducer = reducer<RepositoryDetailsViewState, GetCommitsUseCase.Effect> {
    when (it) {
        is GetCommitsUseCase.Effect.Loading -> copy(
            isLoading = true,
            commitsFetchError = null
        )
        is GetCommitsUseCase.Effect.Error -> copy(
            isLoading = false,
            commitsFetchError = it.message
        )
        is GetCommitsUseCase.Effect.Success -> copy(
            isLoading = false,
            repoOwner = it.repository.owner,
            repoName = it.repository.name,
            commits = it.repository.commits.map { commit -> commit.toUi() }
        )
    }
}

val commitSelectionReducer =
    reducer<RepositoryDetailsViewState, SelectCommitUseCase.CommitSelectionEffect> {
        val commits = commits.map { commit ->
            if (commit.sha == it.sha) {
                commit.copy(isSelected = it.isSelected)
            } else {
                commit
            }
        }
        copy(
            isLoading = false,
            commits = commits,
            isSelectModeOn = true
        )
    }

val closeSelectionModeReducer =
    reducer<RepositoryDetailsViewState, CloseSelectModeUseCase.CloseSelectModeAction> {
        copy(
            commits = commits.map { commit -> commit.copy(isSelected = false) },
            isSelectModeOn = false
        )
    }

val shareCommitsReducer =
    reducer<RepositoryDetailsViewState, ShareCommitsUseCase.ShareCommitsAction> {
        copy(
            shareCommits = SideEffect(commits.filter { it.isSelected })
        )
    }
