package com.androidengineer.weatherinfo.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.androidengineer.utils.ErrorDialoge
import com.androidengineer.utils.ShowLoaderDialogue
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.feature.R
import com.androidengineer.theme.models.AppThemeValues
import com.androidengineer.weatherinfo.domain.model.Forecast
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.weatherinfo.ui.viewmodel.CurrentWeatherViewModel
import com.androidengineer.weatherinfo.ui.WeatherForecast
import com.androidengineer.weatherinfo.ui.models.CurrentWeatherUiState
import com.androidengineer.weatherinfo.navigation.navigateToMoreForecastScreen
import com.androidengineer.weatherinfo.navigation.navigateToSearchWeatherScreen

@Composable
fun CurrentWeather(navHostController: NavHostController) {
    val context = LocalContext.current
    val viewModel: CurrentWeatherViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is CurrentWeatherUiState.Loading -> {
            ShowLoaderDialogue()
        }

        is CurrentWeatherUiState.Error -> {
            ErrorDialoge(dismiss = {
                (context as? Activity)?.finish()
            })
        }

        is CurrentWeatherUiState.Success -> {
            CurrentWeatherUi(
                onSearchClick = {
                    navHostController.navigateToSearchWeatherScreen()
                },
                onMoreForecastClick = {
                    navHostController.navigateToMoreForecastScreen(
                        uiState.forecast.forecast ?: Forecast()
                    )
                },
                weatherForecast = uiState.forecast
            )
        }
    }
}

@Composable
fun CurrentWeatherUi(
    onSearchClick: () -> Unit,
    onMoreForecastClick: () -> Unit,
    themeValues: AppThemeValues = rememberAppThemeValues(),
    weatherForecast: WeatherForecast
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(themeValues.colors.appBackgroundGradientStart.toArgb()),
                        Color(themeValues.colors.appBackgroundGradientEnd.toArgb())
                    )
                )
            )
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onSearchClick() }
                )
                .padding(
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding15,
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding0
                )
                .background(
                    color = themeValues.colors.containerBackground,
                    shape = themeValues.shapes.roundedCorderBackground
                )
                .padding(themeValues.dimens.padding10)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.search_weather),
                contentDescription = ""
            )

            Text(
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding10,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0
                    )
                    .wrapContentWidth()
                    .wrapContentHeight(),
                text = stringResource(R.string.search_weather_placeholder),
                style = themeValues.typography.placeHolderNormal,
                color = themeValues.colors.textBlack
            )
        }

        WeatherForecast(weatherForecast)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onMoreForecastClick() }
                )
                .padding(
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding15,
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding0
                )
                .background(
                    color = themeValues.colors.containerBackground,
                    shape = themeValues.shapes.roundedCorderBackground
                )
                .padding(themeValues.dimens.padding10)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        themeValues.dimens.padding10,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0,
                        themeValues.dimens.padding0
                    )
                    .wrapContentWidth()
                    .wrapContentHeight(),
                text = stringResource(R.string.three_days_weather_forecast_title),
                style = themeValues.typography.textSmallNormal,
                color = themeValues.colors.textBlack
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = ""
            )
        }
    }
}