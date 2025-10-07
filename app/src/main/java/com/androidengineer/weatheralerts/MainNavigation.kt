package com.androidengineer.weatheralerts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.androidengineer.weatherinfo.navigation.CURRENT_WEATHER_NAVIGATION_ROUTE
import com.androidengineer.weatherinfo.navigation.currentWeatherScreen
import com.androidengineer.weatherinfo.navigation.moreForecastScreen
import com.androidengineer.weatherinfo.navigation.searchWeatherScreen

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = CURRENT_WEATHER_NAVIGATION_ROUTE,
        builder = {
            currentWeatherScreen(navHostController)
            moreForecastScreen()
            searchWeatherScreen(navHostController)
        })
}