package pl.mwaszczuk.githubrepotracker.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val owner: String,
)
