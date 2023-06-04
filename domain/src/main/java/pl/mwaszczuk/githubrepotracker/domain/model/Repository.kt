package pl.mwaszczuk.githubrepotracker.domain.model

data class Repository(
    val id: Int,
    val owner: String,
    val name: String,
    val commits: List<Commit>
)
