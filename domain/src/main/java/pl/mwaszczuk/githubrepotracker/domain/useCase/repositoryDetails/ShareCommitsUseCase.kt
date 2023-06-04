package pl.mwaszczuk.githubrepotracker.domain.useCase.repositoryDetails

import kotlinx.coroutines.flow.FlowCollector
import pl.mwaszczuk.githubrepotracker.domain.base.Action
import pl.mwaszczuk.githubrepotracker.domain.base.UseCase
import javax.inject.Inject

class ShareCommitsUseCase @Inject constructor() :
    UseCase<ShareCommitsUseCase.ShareCommitsAction>() {

    override suspend fun FlowCollector<Any>.interact(action: ShareCommitsAction) {
        emit(action)
    }

    object ShareCommitsAction : Action
}
