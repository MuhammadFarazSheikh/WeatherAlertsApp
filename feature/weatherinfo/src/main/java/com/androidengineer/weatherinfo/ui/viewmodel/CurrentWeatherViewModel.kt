package com.androidengineer.weatherinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidengineer.location.LocationProvider
import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.usecases.CurrentWeatherUseCase
import com.androidengineer.weatherinfo.ui.models.CurrentWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurrentWeatherUiState>(CurrentWeatherUiState.Loading)
    val uiState: StateFlow<CurrentWeatherUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            location?.let { loc ->
                getCurrentWeatherData(loc.latitude, loc.longitude)
            }
        }
    }

    fun getCurrentWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            currentWeatherUseCase.invoke(
                apiKey = "5a5bf784f73546f48cb225751252706",
                lat = latitude,
                long = longitude,
                days = 3
            ).collect { result ->
                when (result) {
                    is DomainResult.Loading -> _uiState.value = CurrentWeatherUiState.Loading

                    is DomainResult.Success -> _uiState.value = CurrentWeatherUiState.Success(result.data)

                    is DomainResult.Error -> _uiState.value = CurrentWeatherUiState.Error(result.message)
                }
            }
        }
    }
}