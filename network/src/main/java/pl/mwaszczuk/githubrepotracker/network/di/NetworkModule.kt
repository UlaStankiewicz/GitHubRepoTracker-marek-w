package pl.mwaszczuk.githubrepotracker.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.mwaszczuk.githubrepotracker.network.BuildConfig
import pl.mwaszczuk.githubrepotracker.network.converter.LocalDateSerializer
import pl.mwaszczuk.githubrepotracker.network.converter.OffsetDateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.OffsetDateTime

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}
