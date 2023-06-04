package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails

import androidx.compose.runtime.Immutable
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

@Immutable
data class RepositoryDetailsViewState(
    val isLoading: Boolean = true,
    val commits: List<Commit> = emptyList(),
    val repoOwner: String = "",
    val repoName: String = ""
)
