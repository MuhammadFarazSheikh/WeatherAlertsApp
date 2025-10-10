package com.androidengineer.weatherinfo.domain.usecases

import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.weatherinfo.domain.repositories.SearchWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWeatherUseCase @Inject constructor(
    private val searchWeatherRepository: SearchWeatherRepository
){
    suspend fun invoke(apiKey: String, location: String): Flow<DomainResult<WeatherForecast>> =
        searchWeatherRepository.searchWeatherData(apiKey, location)
}