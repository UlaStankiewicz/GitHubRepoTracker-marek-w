package pl.mwaszczuk.githubrepotracker.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.mwaszczuk.githubrepotracker.network.api.RepositoriesApi
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideRepositoriesApi(retrofit: Retrofit): RepositoriesApi =
        retrofit.create(RepositoriesApi::class.java)
}
