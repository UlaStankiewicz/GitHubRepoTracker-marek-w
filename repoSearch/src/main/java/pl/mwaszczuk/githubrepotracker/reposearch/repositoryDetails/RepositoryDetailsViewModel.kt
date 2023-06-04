package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.mwaszczuk.githubrepotracker.core.base.BaseViewModel
import pl.mwaszczuk.githubrepotracker.core.interact.SideEffect
import pl.mwaszczuk.githubrepotracker.core.interact.reducer
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.CloseSelectModeUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.SelectCommitUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ShareCommitsUseCase
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.mapper.toUi
import javax.inject.Inject

val commitsReducer = reducer<RepositoryDetailsViewState, GetCommitsUseCase.Effect> {
    when (it) {
        is GetCommitsUseCase.Effect.Loading -> copy(isLoading = true)
        is GetCommitsUseCase.Effect.Error -> copy(isLoading = false)
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

val closeSelectionModeReducer = reducer<RepositoryDetailsViewState, CloseSelectModeUseCase.CloseSelectModeAction> {
    copy(
        commits = commits.map { commit -> commit.copy(isSelected = false) },
        isSelectModeOn = false
    )
}

val shareCommitsReducer = reducer<RepositoryDetailsViewState, ShareCommitsUseCase.ShareCommitsAction> {
    copy(
        shareCommits = SideEffect(commits.filter { it.isSelected })
    )
}

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    getCommitsUseCase: GetCommitsUseCase,
    selectCommitUseCase: SelectCommitUseCase,
    closeSelectModeUseCase: CloseSelectModeUseCase,
    shareCommitsUseCase: ShareCommitsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RepositoryDetailsViewState>(
    initialState = RepositoryDetailsViewState(),
    useCases = listOf(getCommitsUseCase, selectCommitUseCase, closeSelectModeUseCase, shareCommitsUseCase),
    reducers = listOf(commitsReducer, commitSelectionReducer, closeSelectionModeReducer, shareCommitsReducer),
) {

    init {
        val owner = checkNotNull(savedStateHandle.get<String>("repoOwner"))
        val name = checkNotNull(savedStateHandle.get<String>("repoName"))
        val id = checkNotNull(savedStateHandle.get<Int>("repoId"))
        publish(GetCommitsUseCase.GetCommitsAction(owner, name, id))
    }
}
