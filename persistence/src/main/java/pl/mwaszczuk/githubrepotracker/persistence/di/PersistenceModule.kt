package pl.mwaszczuk.githubrepotracker.persistence.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.mwaszczuk.githubrepotracker.persistence.GithubDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            "Github_database"
        ).build()
}
