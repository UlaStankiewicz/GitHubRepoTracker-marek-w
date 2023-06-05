package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.mwaszczuk.githubrepotracker.core.base.BaseViewModel
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.CloseSelectModeUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.GetCommitsUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.SelectCommitUseCase
import pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails.ShareCommitsUseCase
import javax.inject.Inject

const val REPOSITORY_DETAILS_SCREEN_OWNER_ARG = "repoOwner"
const val REPOSITORY_DETAILS_SCREEN_NAME_ARG = "repoName"
const val REPOSITORY_DETAILS_SCREEN_ID_ARG = "repoId"

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    getCommitsUseCase: GetCommitsUseCase,
    selectCommitUseCase: SelectCommitUseCase,
    closeSelectModeUseCase: CloseSelectModeUseCase,
    shareCommitsUseCase: ShareCommitsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RepositoryDetailsViewState>(
    initialState = RepositoryDetailsViewState(
        repoOwner = checkNotNull(savedStateHandle.get<String>(REPOSITORY_DETAILS_SCREEN_OWNER_ARG)),
        repoName = checkNotNull(savedStateHandle.get<String>(REPOSITORY_DETAILS_SCREEN_NAME_ARG)),
        repoId = checkNotNull(savedStateHandle.get<Int>(REPOSITORY_DETAILS_SCREEN_ID_ARG))
    ),
    useCases = listOf(
        getCommitsUseCase,
        selectCommitUseCase,
        closeSelectModeUseCase,
        shareCommitsUseCase
    ),
    reducers = listOf(
        commitsReducer,
        commitSelectionReducer,
        closeSelectionModeReducer,
        shareCommitsReducer
    ),
) {

    init {
        publish(
            GetCommitsUseCase.GetCommitsAction(
                state.value.repoOwner,
                state.value.repoName,
                state.value.repoId
            )
        )
    }
}
