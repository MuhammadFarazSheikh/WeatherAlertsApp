package com.androidengineer.weatherinfo.ui.models

import com.androidengineer.weatherinfo.domain.model.WeatherForecast

sealed class SearchWeatherUiState {
    object Idle : SearchWeatherUiState()
    object Loading : SearchWeatherUiState()
    data class Success(val forecast: WeatherForecast) : SearchWeatherUiState()
    data class Error(val message: String) : SearchWeatherUiState()
}