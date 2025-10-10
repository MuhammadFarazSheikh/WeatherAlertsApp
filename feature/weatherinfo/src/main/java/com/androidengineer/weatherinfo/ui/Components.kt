package com.androidengineer.weatherinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.androidengineer.weatherinfo.domain.model.WeatherForecast
import com.androidengineer.feature.R
import com.androidengineer.theme.models.AppThemeValues
import com.androidengineer.theme.rememberAppThemeValues
import com.androidengineer.utils.DATE_TIME_FORMAT_NEW
import com.androidengineer.utils.DATE_TIME_FORMAT_OLD
import com.androidengineer.utils.formatDateTime
import com.androidengineer.utils.getWindDirection
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun WeatherDetail(
    icon: Int, title: String, value: String, themeValues: AppThemeValues = rememberAppThemeValues()
) = Row(
    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .padding(
            themeValues.dimens.padding0,
            themeValues.dimens.padding10,
            themeValues.dimens.padding0,
            themeValues.dimens.padding0
        )
        .background(
            color = colorResource(R.color.light_white), shape = themeValues.shapes.roundedCorderBackground
        )
        .padding(themeValues.dimens.padding10)
        .fillMaxWidth()
        .wrapContentHeight(), content = {

        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(icon),
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
            text = title,
            style = themeValues.typography.textSmallNormal,
            color = themeValues.colors.textBlack
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            text = value,
            style = themeValues.typography.textSmallNormal,
            color = themeValues.colors.textBlack
        )
    }
)

@Composable
fun WeatherForecast(
    state: WeatherForecast,
    themeValues: AppThemeValues = rememberAppThemeValues(),
) = Column (
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth(),
    content = {
        Text(
            textAlign = TextAlign.Center, modifier = Modifier.padding(
                themeValues.dimens.padding0,
                themeValues.dimens.padding20,
                themeValues.dimens.padding0,
                themeValues.dimens.padding0
            ), text ="${state.location.name}\n${state.location.country}"
            , style = themeValues.typography.textLargeNormal, color = themeValues.colors.textBlack
        )

        Text(
            modifier = Modifier.padding(
                themeValues.dimens.padding0,
                themeValues.dimens.padding10,
                themeValues.dimens.padding0,
                themeValues.dimens.padding0
            ), text = formatDateTime(
                state.location.localtime, DATE_TIME_FORMAT_OLD, DATE_TIME_FORMAT_NEW
            ), style = themeValues.typography.textSmallBold, color = themeValues.colors.textBlack
        )

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(
                    themeValues.dimens.padding0,
                    themeValues.dimens.padding20,
                    themeValues.dimens.padding0,
                    themeValues.dimens.padding0
                )
                .wrapContentWidth()
                .wrapContentHeight(), content = {
                Image(
                    modifier = Modifier.size(70.dp),
                    painter = rememberAsyncImagePainter(state.current.condition.fullIconUrl),
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
                    text = state.current.tempCentigrade,
                    style = themeValues.typography.textExtraLargeBold,
                    color = themeValues.colors.textBlack
                )
            })

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding0,
                    themeValues.dimens.padding40,
                    themeValues.dimens.padding0
                )
                .fillMaxWidth()
                .wrapContentHeight(), content = {

                WeatherDetail(
                    icon = R.drawable.humidity,
                    title = "Humidity",
                    value = state.current.humidityPercent
                )

                WeatherDetail(
                    icon = R.drawable.wind,
                    title = "Wind",
                    value = state.current.windSpeedKMPH
                )

                WeatherDetail(
                    icon = R.drawable.wind_direction,
                    title = "Direction",
                    value = getWindDirection(state.current.wind_dir)
                )

                WeatherDetail(
                    icon = R.drawable.feels_like,
                    title = "Feels",
                    value = state.current.feelsLikeCentigrade
                )

            }
        )

        GoogleMaps(
            state.location.lat,
            state.location.lon,
            state.location.name,
            state.location.country
        )
    }
)

@Composable
fun GoogleMaps(
    lat: Double,
    lon: Double,
    city: String,
    country: String,
    themeValues: AppThemeValues = rememberAppThemeValues()
) {
    val location = LatLng(lat, lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    GoogleMap(
        modifier = Modifier
            .padding(
                themeValues.dimens.padding40,
                themeValues.dimens.padding20,
                themeValues.dimens.padding40,
                themeValues.dimens.padding0
            )
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp)),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            scrollGesturesEnabled = false,
        )
    ) {
        Marker(
            state = MarkerState(position = location), title = "$city $country"
        )
    }
}