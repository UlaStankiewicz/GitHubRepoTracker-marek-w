package pl.mwaszczuk.githubrepotracker.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class CommitEntity(
    @PrimaryKey(autoGenerate = false)
    val sha: String,
    val message: String,
    val authorName: String,
    val repositoryId: Int
)
