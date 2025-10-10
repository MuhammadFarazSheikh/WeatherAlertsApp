package com.androidengineer.weatherinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.androidengineer.weatherinfo.domain.model.Forecast
import com.androidengineer.weatherinfo.ui.models.MoreForecastUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoreForecastViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MoreForecastUiState())
    val uiState: StateFlow<MoreForecastUiState> = _uiState.asStateFlow()

    fun initForecast(forecast: Forecast) {
        val tabs = forecast.forecastday.mapNotNull { it?.date }
        _uiState.update {
            it.copy(
                tabs = tabs,
                currentRoute = tabs.firstOrNull() ?: "",
                selectedIndex = 0
            )
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(selectedIndex = index, currentRoute = it.tabs.getOrNull(index) ?: "")
        }
    }

}