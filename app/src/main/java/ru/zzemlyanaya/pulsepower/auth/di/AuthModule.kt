package ru.zzemlyanaya.pulsepower.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import ru.zzemlyanaya.pulsepower.auth.data.api.AuthApi
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AuthModule {

}