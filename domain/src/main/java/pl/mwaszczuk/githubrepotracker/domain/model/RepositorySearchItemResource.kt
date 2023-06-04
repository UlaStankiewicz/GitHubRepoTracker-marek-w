package pl.mwaszczuk.githubrepotracker.domain.model

data class RepositorySearchItemResource(
    val id: Int,
    val owner: String,
    val name: String
)
