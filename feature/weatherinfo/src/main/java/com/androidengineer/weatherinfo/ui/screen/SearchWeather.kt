package com.androidengineer.weatherinfo.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.androidengineer.theme.models.AppThemeValues
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.weatherinfo.ui.WeatherForecast
import com.androidengineer.weatherinfo.ui.models.SearchWeatherUiState
import com.androidengineer.weatherinfo.ui.viewmodel.SearchWeatherViewModel
import com.androidengineer.feature.R
import com.androidengineer.utils.ErrorDialoge

@Composable
fun SearchWeatherScreen(navController: NavHostController) {
    val viewModel: SearchWeatherViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery = rememberSaveable { mutableStateOf("") }

    SearchWeatherScreenUi(
        uiState = uiState,
        query = searchQuery.value,
        onQueryChange = { searchQuery.value = it },
        onSearch = viewModel::searchWeather,
        onErrorDismiss = { navController.popBackStack() })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchWeatherScreenUi(
    uiState: SearchWeatherUiState,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onErrorDismiss: () -> Unit,
    themeValues: AppThemeValues = rememberAppThemeValues(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(themeValues.colors.appBackgroundGradientStart.toArgb()),
                        Color(themeValues.colors.appBackgroundGradientEnd.toArgb())
                    )
                )
            )
            .fillMaxSize()
            .padding(horizontal = themeValues.dimens.padding10, vertical = themeValues.dimens.padding10)
    ) {

        SearchBar(
            query = query, onQueryChange = onQueryChange, onSearchClick = { onSearch(query) })

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedContent(
            targetState = uiState,
            contentAlignment = Alignment.Center,
            transitionSpec = { bottomToTopTransition() }) { state ->
            when (state) {
                is SearchWeatherUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = themeValues.dimens.padding10)
                            .size(themeValues.dimens.progressIndicatorSize),
                        color = themeValues.colors.progressIndicatorColor,
                        strokeWidth = themeValues.dimens.progressIndicatorStrokeWidth
                    )
                }

                is SearchWeatherUiState.Success -> {
                    WeatherForecast(state.forecast)
                }

                is SearchWeatherUiState.Error -> {
                    ErrorDialoge (
                        dismiss = { onErrorDismiss() }
                    )
                }

                is SearchWeatherUiState.Idle -> {
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun bottomToTopTransition() = slideInVertically(
    initialOffsetY = { fullHeight -> fullHeight }) with slideOutVertically(
    targetOffsetY = { fullHeight -> -fullHeight })

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    themeValues: AppThemeValues = rememberAppThemeValues(),
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(R.string.search_weather_placeholder),
                style = themeValues.typography.placeHolderNormal,
                color = themeValues.colors.textBlack
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search icon"
                )
            }
        })
}