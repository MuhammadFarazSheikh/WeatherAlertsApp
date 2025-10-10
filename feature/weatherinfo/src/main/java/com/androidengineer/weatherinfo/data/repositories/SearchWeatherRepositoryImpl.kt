package com.androidengineer.weatherinfo.data.repositories

import com.androidengineer.weatherinfo.data.remote.RemoteDataSource
import com.androidengineer.network.Response
import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.weatherinfo.domain.repositories.SearchWeatherRepository
import com.androidengineer.weatherinfo.data.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchWeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SearchWeatherRepository {
    override suspend fun searchWeatherData(
        apiKey: String, location: String
    ): Flow<DomainResult<WeatherForecast>> = flow{
        emit(DomainResult.Loading)
        val result = remoteDataSource.getSearchWeatherData(apiKey, location)
        when (result) {
            is Response.Success -> emit(DomainResult.Success(result.data.toDomainModel()))
            is Response.Error -> emit(DomainResult.Error(result.message))
        }
    }
}