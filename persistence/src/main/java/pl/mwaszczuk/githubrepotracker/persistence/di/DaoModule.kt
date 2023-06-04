package pl.mwaszczuk.githubrepotracker.persistence.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.mwaszczuk.githubrepotracker.persistence.GithubDatabase

@InstallIn(ViewModelComponent::class)
@Module
class DaoModule {

    @Provides
    fun provideRepositoryDao(database: GithubDatabase) = database.repositoryDao()
}
