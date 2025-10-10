package com.androidengineer.weatherinfo.ui.models

data class MoreForecastUiState(
    val tabs: List<String> = emptyList(),
    val selectedIndex: Int = 0,
    val currentRoute: String = ""
)
