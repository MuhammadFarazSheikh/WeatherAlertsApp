package com.androidengineer.weatherinfo.ui.models

import com.androidengineer.weatherinfo.domain.model.WeatherForecast

sealed class CurrentWeatherUiState {
    object Loading : CurrentWeatherUiState()
    data class Success(val forecast: WeatherForecast) : CurrentWeatherUiState()
    data class Error(val message: String) : CurrentWeatherUiState()
}