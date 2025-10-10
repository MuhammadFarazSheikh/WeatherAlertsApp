package com.androidengineer.weatherinfo.domain.usecases

import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.weatherinfo.domain.repositories.CurrentWeatherRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CurrentWeatherUseCase @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository
) {
    suspend operator fun invoke(apiKey: String, lat: Double, long: Double, days: Int): Flow<DomainResult<WeatherForecast>> =
        currentWeatherRepository.getWeatherData(apiKey, lat, long, days)
}