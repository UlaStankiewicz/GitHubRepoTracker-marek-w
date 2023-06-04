package pl.mwaszczuk.githubrepotracker.persistence.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.mwaszczuk.githubrepotracker.persistence.model.CommitEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryEntity
import pl.mwaszczuk.githubrepotracker.persistence.model.RepositoryWithCommits

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(repository: RepositoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCommits(commits: List<CommitEntity>)

    @Query("SELECT * FROM repository WHERE name = :name AND owner = :owner LIMIT 1")
    suspend fun getRepository(owner: String, name: String): RepositoryEntity?

    @Transaction
    @Query("SELECT * FROM repository WHERE id = :repoId")
    fun getRepositoryWithCommits(repoId: Int): Flow<RepositoryWithCommits>

    @Query("SELECT * FROM repository")
    suspend fun getSearchHistory(): List<RepositoryEntity>
}
