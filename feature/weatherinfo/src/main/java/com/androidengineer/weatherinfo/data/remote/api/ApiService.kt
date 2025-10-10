package com.androidengineer.weatherinfo.data.remote.api

import com.androidengineer.weatherinfo.data.remote.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val WEATHER_FORECAST_ENDPOINT = "forecast.json"
const val SEARCH_WEATHER_ENDPOINT = "current.json"

interface ApiService {

    @GET(WEATHER_FORECAST_ENDPOINT)
    suspend fun getWeatherData(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): WeatherForecastResponse

    @GET(SEARCH_WEATHER_ENDPOINT)
    suspend fun getSearchWeatherData(
        @Query("key") apiKey: String,
        @Query("q") location: String,
    ): WeatherForecastResponse
}