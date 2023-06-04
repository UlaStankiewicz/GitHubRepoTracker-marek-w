package pl.mwaszczuk.githubrepotracker.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.mwaszczuk.githubrepotracker.domain.repository.RepositoriesRepository
import pl.mwaszczuk.githubrepotracker.network.ResponseMapper
import pl.mwaszczuk.githubrepotracker.network.api.RepositoriesApi
import pl.mwaszczuk.githubrepotracker.network.pl.mwaszczuk.githubrepotracker.data.repository.RepositoriesRepositoryImpl
import pl.mwaszczuk.githubrepotracker.persistence.dao.RepositoryDao

@InstallIn(ViewModelComponent::class)
@Module
class DataModule {

    @Provides
    fun provideRepositoriesRepository(
        repositoryApi: RepositoriesApi,
        repositoryDao: RepositoryDao,
        responseMapper: ResponseMapper
    ) : RepositoriesRepository =
        RepositoriesRepositoryImpl(repositoryApi, repositoryDao, responseMapper)

    @Provides
    fun provideResponseMapper() = ResponseMapper()
}
