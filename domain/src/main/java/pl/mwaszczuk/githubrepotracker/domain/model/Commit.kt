package pl.mwaszczuk.githubrepotracker.domain.model

data class Commit(
    val sha: String,
    val message: String,
    val authorName: String
)
