package pl.mwaszczuk.githubrepotracker.domain.model

data class RepositoryResource(
    val id: Int,
    val owner: String,
    val name: String,
    val commits: List<CommitResource>
)
