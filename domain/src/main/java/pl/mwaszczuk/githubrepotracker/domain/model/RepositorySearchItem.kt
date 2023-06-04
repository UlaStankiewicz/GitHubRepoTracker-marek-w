package pl.mwaszczuk.githubrepotracker.domain.model

data class RepositorySearchItem(
    val id: Int,
    val owner: String,
    val name: String
)
