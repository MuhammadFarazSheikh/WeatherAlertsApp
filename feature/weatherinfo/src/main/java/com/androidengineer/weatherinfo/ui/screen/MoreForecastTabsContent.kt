package com.androidengineer.weatherinfo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.androidengineer.weatherinfo.domain.model.Forecastday
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.feature.R
import com.androidengineer.weatherinfo.ui.WeatherDetail

@Composable
fun MoreforecastTabsContent(forecast: Forecastday) {

    val themeValues = rememberAppThemeValues()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(themeValues.colors.appBackgroundGradientStart.toArgb()),
                        Color(themeValues.colors.appBackgroundGradientEnd.toArgb())
                    )
                )
            )
            .fillMaxWidth()
            .fillMaxHeight(), content = {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding20,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0
                    )
                    .wrapContentWidth()
                    .wrapContentHeight(),
                content = {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = rememberAsyncImagePainter(forecast.day?.condition?.fullIconUrl),
                        contentDescription = "Weather icon"
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                themeValues.dimens.padding20,
                                themeValues.dimens.padding10,
                                themeValues.dimens.padding10,
                                themeValues.dimens.padding10
                            )
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        text = forecast?.day?.tempCentigrade!!,
                        style = themeValues.typography.textExtraLargeBold,
                        color = themeValues.colors.textBlack
                    )
                })

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding40,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding40,
                        themeValues.dimens.padding0
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                content = {

                    WeatherDetail(
                        icon = R.drawable.humidity,
                        title = stringResource(R.string.humidity_title),
                        value = forecast.day?.humidityPercent!!
                    )

                    WeatherDetail(
                        icon = R.drawable.wind,
                        title = stringResource(R.string.wind_title),
                        value = forecast.day?.windSpeedKMPH!!
                    )

                    WeatherDetail(
                        icon = R.drawable.sunrise,
                        title = stringResource(R.string.sunrise_title),
                        value = forecast.astro?.sunrise!!
                    )

                    WeatherDetail(
                        icon = R.drawable.sunset,
                        title = stringResource(R.string.sunset_title),
                        value = forecast.astro?.sunset!!
                    )
                }
            )

            Text(
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding20,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0
                    )
                    .wrapContentWidth()
                    .wrapContentHeight(),
                text = stringResource(R.string.hourly_forecast_title),
                style = themeValues.typography.textSmallNormal,
                color = themeValues.colors.textBlack
            )

            LazyRow(
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding20,
                        themeValues.dimens.padding20,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(), content = {
                    items(forecast.hour?.size!!) { index ->
                        HourlyWeatherForecast(forecast?.hour?.get(index)!!)
                    }
                }
            )
        }
    )
}