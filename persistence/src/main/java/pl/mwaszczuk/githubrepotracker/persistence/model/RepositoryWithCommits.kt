package pl.mwaszczuk.githubrepotracker.persistence.model

import androidx.room.Embedded
import androidx.room.Relation

data class RepositoryWithCommits(
    @Embedded val repository: RepositoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "repositoryId"
    )
    val commits: List<CommitEntity>
)
