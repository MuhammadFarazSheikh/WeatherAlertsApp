package com.androidengineer.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColors(
    val appBackgroundGradientStart: Color,
    val appBackgroundGradientEnd: Color,
    val textBlack: Color,
    val dialogueBackground: Color,
    val progressIndicatorColor: Color,
    val placeHolderText: Color,
    val containerBackground: Color
)

data class AppDimens(
    val padding0: Dp,
    val padding10: Dp,
    val padding15: Dp,
    val padding20: Dp,
    val padding40: Dp,
    val padding50: Dp,

    val progressIndicatorSize: Dp,
    val progressIndicatorStrokeWidth: Dp
)

data class AppShapes(
    val roundedCorderBackground: Shape,
)

data class AppTypography(
    val placeHolderNormal: TextStyle,
    val tabsTextBold: TextStyle,
    val textLargeNormal: TextStyle,
    val textSmallBold: TextStyle,
    val textExtraLargeBold: TextStyle,
    val textSmallNormal: TextStyle
)