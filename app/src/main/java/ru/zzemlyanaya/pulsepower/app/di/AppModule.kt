package ru.zzemlyanaya.pulsepower.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.auth.data.api.AuthApi
import ru.zzemlyanaya.pulsepower.auth.domain.interactor.AuthInteractor
import ru.zzemlyanaya.pulsepower.core.http.AuthCredentialsCache
import ru.zzemlyanaya.pulsepower.core.http.interceptor.AuthInterceptor
import ru.zzemlyanaya.pulsepower.home.data.api.StoreApi
import ru.zzemlyanaya.pulsepower.profile.data.api.UserApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Core dependencies

    @Singleton
    @Provides
    fun providesNavigationRouter() = NavigationRouter()

    // Network

    @Provides
    fun providesBaseUrl(): String = "http://37.131.217.22:25252/api/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideAuthCredentialsCache() = AuthCredentialsCache()

    @Singleton
    @Provides
    fun providesAuthInterceptor(cache: AuthCredentialsCache) = AuthInterceptor(cache)

    @Singleton
    @Provides
    fun providesOkkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .followSslRedirects(true)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()


    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    // Feature APIs

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideStoreApi(retrofit: Retrofit): StoreApi = retrofit.create(StoreApi::class.java)
}