package pl.mwaszczuk.githubrepotracker.domain.model

data class CommitResource(
    val sha: String,
    val message: String,
    val authorName: String
)
