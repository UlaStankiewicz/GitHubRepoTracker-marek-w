package pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails

import kotlinx.coroutines.flow.FlowCollector
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import javax.inject.Inject

class CloseSelectModeUseCase @Inject constructor() :
    UseCase<CloseSelectModeUseCase.CloseSelectModeAction>() {

    override suspend fun FlowCollector<Any>.interact(action: CloseSelectModeAction) {
        emit(action)
    }

    object CloseSelectModeAction : Action
}
