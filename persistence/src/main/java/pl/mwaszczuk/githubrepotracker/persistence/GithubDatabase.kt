package pl.mwaszczuk.githubrepotracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.mwaszczuk.githubrepotracker.persistence.dao.RepositoryDao
import pl.mwaszczuk.githubrepotracker.persistence.model.CommitEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryEntity

@Database(
    entities = [CommitEntity::class, RepositoryEntity::class],
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
}
