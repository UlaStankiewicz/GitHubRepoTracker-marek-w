package pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails

import kotlinx.coroutines.flow.FlowCollector
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import javax.inject.Inject

class SelectCommitUseCase @Inject constructor() :
    UseCase<SelectCommitUseCase.SelectCommitAction>() {

    data class CommitSelectionEffect(
        val sha: String,
        val isSelected: Boolean
    )

    override suspend fun FlowCollector<Any>.interact(action: SelectCommitAction) {
        emit(CommitSelectionEffect(action.sha, action.isSelected))
    }

    data class SelectCommitAction(
        val sha: String,
        val isSelected: Boolean
    ) : Action
}
