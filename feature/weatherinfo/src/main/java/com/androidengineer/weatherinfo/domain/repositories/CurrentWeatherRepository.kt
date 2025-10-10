package com.androidengineer.weatherinfo.domain.repositories

import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {
    suspend fun getWeatherData(apiKey: String, lat: Double, long: Double, days: Int): Flow<DomainResult<WeatherForecast>>
}