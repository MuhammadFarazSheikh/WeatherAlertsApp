package com.androidengineer.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidengineer.theme.models.AppThemeValues


val LocalThemeColors = staticCompositionLocalOf<AppColors> { error("No Colors provided") }
val LocalThemeDimens = staticCompositionLocalOf<AppDimens> { error("No Dimens provided") }
val LocalThemeTypography = staticCompositionLocalOf<AppTypography> { error("No Typography provided") }
val LocalThemeShapes = staticCompositionLocalOf<AppShapes> { error("No Shapes provided") }

object AppThemeDefaults {

    fun colors() = AppColors(
        appBackgroundGradientStart = Color(0xFFB3E5FC),
        appBackgroundGradientEnd = Color(0xFFE1F5FE),
        textBlack = Color.Black,
        dialogueBackground = Color.White,
        progressIndicatorColor = Color.Black,
        placeHolderText = Color.Black.copy(alpha = 0.6f),
        containerBackground = Color(0x50FFFFFF)
    )

    fun dimens() = AppDimens(
        padding0 = 0.dp,
        padding10 = 10.dp,
        padding15 = 15.dp,
        padding20 = 20.dp,
        padding40 = 40.dp,
        padding50 = 50.dp,
        progressIndicatorSize = 40.dp,
        progressIndicatorStrokeWidth = 2.dp
    )

    fun typography() = AppTypography(
        placeHolderNormal = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        tabsTextBold = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        ),
        textLargeNormal = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        ),
        textSmallBold = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        ),
        textExtraLargeBold = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        ),
        textSmallNormal = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    )

    fun shapes() = AppShapes(
        roundedCorderBackground = RoundedCornerShape(5.dp)
    )
}

@Composable
fun rememberAppThemeValues(): AppThemeValues {
    return AppThemeValues(
        colors = LocalThemeColors.current,
        dimens = LocalThemeDimens.current,
        typography = LocalThemeTypography.current,
        shapes = LocalThemeShapes.current
    )
}

@Composable
fun WeatherAppTheme(
    colors: AppColors = AppThemeDefaults.colors(),
    dimens: AppDimens = AppThemeDefaults.dimens(),
    typography: AppTypography = AppThemeDefaults.typography(),
    shapes: AppShapes = AppThemeDefaults.shapes(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalThemeColors provides colors,
        LocalThemeDimens provides dimens,
        LocalThemeTypography provides typography,
        LocalThemeShapes provides shapes,
        content = content
    )
}