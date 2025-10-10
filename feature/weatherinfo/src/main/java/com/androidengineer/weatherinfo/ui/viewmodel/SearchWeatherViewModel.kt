package com.androidengineer.weatherinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidengineer.weatherinfo.domain.model.DomainResult
import com.androidengineer.weatherinfo.domain.usecases.SearchWeatherUseCase
import com.androidengineer.weatherinfo.ui.models.SearchWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val searchWeatherUseCase: SearchWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchWeatherUiState>(SearchWeatherUiState.Idle)
    val uiState: StateFlow<SearchWeatherUiState> = _uiState.asStateFlow()

    fun searchWeather(searchQuery: String) {
        if (searchQuery.isBlank()) return

        viewModelScope.launch {
            searchWeatherUseCase.invoke("5a5bf784f73546f48cb225751252706", searchQuery)
                .collect { result ->
                    _uiState.value = when (result) {
                        is DomainResult.Loading -> SearchWeatherUiState.Loading
                        is DomainResult.Success -> SearchWeatherUiState.Success(result.data)
                        is DomainResult.Error -> SearchWeatherUiState.Error(result.message)
                    }
                }
        }
    }
}