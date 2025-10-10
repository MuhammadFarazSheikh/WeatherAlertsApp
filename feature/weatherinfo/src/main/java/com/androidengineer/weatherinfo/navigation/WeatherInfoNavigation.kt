package com.androidengineer.weatherinfo.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.androidengineer.weatherinfo.ui.screen.CurrentWeather
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.androidengineer.weatherinfo.domain.model.Forecast
import com.androidengineer.weatherinfo.ui.screen.MoreForecastScreen
import kotlinx.serialization.json.Json
import com.androidengineer.weatherinfo.ui.screen.SearchWeatherScreen

const val CURRENT_WEATHER_NAVIGATION_ROUTE = "current_weather"
const val MORE_FORECAST_NAVIGATION_ROUTE = "more_forecast"
const val SEARCH_WEATHER_NAVIGATION_ROUTE = "search_weather"

fun NavHostController.navigateToCurrentWeatherScreen(
) = navigate(CURRENT_WEATHER_NAVIGATION_ROUTE)

fun NavHostController.navigateToSearchWeatherScreen(
) = navigate(SEARCH_WEATHER_NAVIGATION_ROUTE)

fun NavHostController.navigateToMoreForecastScreen(
    forecast: Forecast
) {
    val json = Uri.encode(Json.encodeToString(forecast))
    navigate("$MORE_FORECAST_NAVIGATION_ROUTE/$json")
}

fun NavGraphBuilder.searchWeatherScreen(navHostController: NavHostController) {
    composable(SEARCH_WEATHER_NAVIGATION_ROUTE) {
        SearchWeatherScreen(navHostController)
    }
}

fun NavGraphBuilder.moreForecastScreen() {
    composable(
        "$MORE_FORECAST_NAVIGATION_ROUTE/{forecastData}",
        arguments = listOf(navArgument("forecastData") { type = NavType.StringType })
    ) { backStackEntry ->
        val encodedJson = backStackEntry.arguments?.getString("forecastData")
        val forecastData = encodedJson?.let { json ->
            Json.decodeFromString<Forecast>(Uri.decode(json))
        }

        forecastData?.let {
            MoreForecastScreen(it)
        }
    }
}

fun NavGraphBuilder.currentWeatherScreen(navHostController: NavHostController) {
    composable(CURRENT_WEATHER_NAVIGATION_ROUTE) {
        CurrentWeather(navHostController)
    }
}