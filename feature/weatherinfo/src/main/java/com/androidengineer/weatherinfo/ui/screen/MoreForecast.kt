package com.androidengineer.weatherinfo.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidengineer.theme.models.AppThemeValues
import com.androidengineer.weatherinfo.domain.model.Forecast
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.utils.DATE_FORMAT_NEW
import com.androidengineer.utils.DATE_FORMAT_OLD
import com.androidengineer.utils.formatDateTime
import com.androidengineer.weatherinfo.ui.models.MoreForecastUiState
import com.androidengineer.weatherinfo.ui.viewmodel.MoreForecastViewModel

@Composable
fun MoreForecastScreen(
    forecast: Forecast,
    viewModel: MoreForecastViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(forecast) {
        viewModel.initForecast(forecast)
    }

    if (uiState.tabs.isNotEmpty()) {
        MoreForecastTabsUi(
            forecast = forecast, uiState = uiState, onTabSelected = { index ->
                viewModel.onTabSelected(index)
                val newRoute = uiState.tabs[index]
                navController.navigate(newRoute) {
                    popUpTo(uiState.tabs.first()) { inclusive = false }
                    launchSingleTop = true
                }
            }, navController = navController
        )
    }
}

@Composable
fun MoreForecastTabsUi(
    themeValues: AppThemeValues = rememberAppThemeValues(),
    forecast: Forecast,
    uiState: MoreForecastUiState,
    onTabSelected: (Int) -> Unit,
    navController: NavHostController
) {
    val colors = themeValues.colors

    Scaffold(
        topBar = {
            TabRow(
                containerColor = Color(colors.appBackgroundGradientStart.toArgb()),
                contentColor = Color.Black,
                divider = {},
                selectedTabIndex = uiState.selectedIndex,
                indicator = { tabPositions ->
                    if (uiState.selectedIndex < tabPositions.size) {
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedIndex]),
                            color = Color(colors.appBackgroundGradientEnd.toArgb()),
                            height = 2.dp
                        )
                    }
                }) {
                uiState.tabs.forEachIndexed { index, date ->
                    Tab(
                        selected = uiState.selectedIndex == index,
                        onClick = { onTabSelected(index) },
                        text = {
                            Text(
                                text = formatDateTime(date, DATE_FORMAT_OLD, DATE_FORMAT_NEW),
                                color = themeValues.colors.textBlack,
                                style = themeValues.typography.tabsTextBold
                            )
                        }
                    )
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = uiState.tabs.first(),
            modifier = Modifier.padding(innerPadding)
        ) {
            uiState.tabs.forEach { date ->
                composable(date) {
                    forecast.forecastday.find { it?.date == date }?.let {
                        MoreforecastTabsContent(it)
                    }
                }
            }
        }
    }
}