package com.codelytical.notyapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codelytical.notyapp.R

private val gilroy = FontFamily(
    Font(R.font.gilroy_regular),
    Font(R.font.gilroy_semibold, FontWeight.W600),
    Font(R.font.gilroy_bold, FontWeight.Bold)
)

private val universalStd = FontFamily(
    Font(R.font.universal_std)
)

val typography = Typography(
    h4 = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = universalStd,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = universalStd,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)
