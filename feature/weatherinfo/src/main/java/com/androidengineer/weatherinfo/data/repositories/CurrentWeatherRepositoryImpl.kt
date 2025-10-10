package com.androidengineer.weatherinfo.data.repositories

import com.androidengineer.weatherinfo.data.remote.RemoteDataSource
import com.androidengineer.network.Response
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.weatherinfo.domain.repositories.CurrentWeatherRepository
import com.androidengineer.weatherinfo.data.mapper.toDomainModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.androidengineer.weatherinfo.domain.model.DomainResult

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CurrentWeatherRepository {
    override suspend fun getWeatherData(apiKey: String, lat: Double, long: Double, days: Int): Flow<DomainResult<WeatherForecast>> =
        flow {
            emit(DomainResult.Loading)
            val result = remoteDataSource.getWeatherData(apiKey, lat, long, days)
            when (result) {
                is Response.Success -> emit(DomainResult.Success(result.data.toDomainModel()))
                is Response.Error -> emit(DomainResult.Error(result.message))
            }
        }
}