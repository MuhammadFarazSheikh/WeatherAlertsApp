package com.androidengineer.weatherinfo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.androidengineer.weatherinfo.domain.model.Hour
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.feature.R
import com.androidengineer.weatherinfo.ui.WeatherDetail

@Composable
fun HourlyWeatherForecast(hour: Hour) {

    val themeValues = rememberAppThemeValues()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(
                themeValues.dimens.padding0,
                themeValues.dimens.padding0,
                themeValues.dimens.padding15,
                themeValues.dimens.padding0
            )
            .width(280.dp)
            .wrapContentHeight(), content = {

            WeatherDetail(
                icon = R.drawable.clock, title = stringResource(R.string.time_title), value = hour.time.split(" ").last()
            )

            WeatherDetail(
                icon = R.drawable.feels_like, title = stringResource(R.string.temperature_title), value = hour.tempCentigrade
            )

            WeatherDetail(
                icon = R.drawable.humidity, title = stringResource(R.string.humidity_title), value = hour.humidityPercent
            )

            WeatherDetail(
                icon = R.drawable.feels_like, title = stringResource(R.string.feels_title), value = hour.feelsLikeCentigrade
            )
        })
}