package com.androidengineer.weatherinfo.di

import com.androidengineer.weatherinfo.data.remote.RemoteDataSource
import com.androidengineer.weatherinfo.data.remote.api.ApiService
import com.androidengineer.weatherinfo.data.repositories.CurrentWeatherRepositoryImpl
import com.androidengineer.weatherinfo.data.repositories.SearchWeatherRepositoryImpl
import com.androidengineer.weatherinfo.domain.repositories.CurrentWeatherRepository
import com.androidengineer.weatherinfo.domain.repositories.SearchWeatherRepository
import com.androidengineer.weatherinfo.domain.usecases.CurrentWeatherUseCase
import com.androidengineer.weatherinfo.domain.usecases.SearchWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherInfoModule {

    @Provides
    @Singleton
    fun getRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun getCurrentWeatherRepository(remoteDataSource: RemoteDataSource): CurrentWeatherRepository =
        CurrentWeatherRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun getCurrentWeatherUseCase(currentWeatherRepository: CurrentWeatherRepository): CurrentWeatherUseCase =
        CurrentWeatherUseCase(currentWeatherRepository)

    @Provides
    @Singleton
    fun getSearchWeatherRepository(remoteDataSource: RemoteDataSource): SearchWeatherRepository =
        SearchWeatherRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun getSearchWeatherUseCase(searchWeatherRepository: SearchWeatherRepository): SearchWeatherUseCase =
        SearchWeatherUseCase(searchWeatherRepository)

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}