package com.androidengineer.weatherinfo.domain.repositories

import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface SearchWeatherRepository {
    suspend fun searchWeatherData(apiKey: String, location: String): Flow<DomainResult<WeatherForecast>>
}