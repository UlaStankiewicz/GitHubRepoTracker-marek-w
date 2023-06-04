package pl.mwaszczuk.githubrepotracker.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.mwaszczuk.githubrepotracker.core.base.DispatchersProviderImpl
import pl.mwaszczuk.githubrepotracker.domain.base.DispatchersProvider

@InstallIn(ViewModelComponent::class)
@Module
class CoreModule {

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl()
}
