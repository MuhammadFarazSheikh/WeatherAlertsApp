package com.androidengineer.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidengineer.theme.models.AppThemeValues
import com.androidengineer.theme.rememberAppThemeValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowLoaderDialogue(
    themeValues: AppThemeValues = rememberAppThemeValues()
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier
            .background(color = themeValues.colors.dialogueBackground, shape = themeValues.shapes.roundedCorderBackground)
            .wrapContentWidth()
            .wrapContentHeight(),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(themeValues.dimens.padding0, themeValues.dimens.padding10)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                content = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(themeValues.dimens.progressIndicatorSize)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        color = themeValues.colors.progressIndicatorColor,
                        strokeWidth = themeValues.dimens.progressIndicatorStrokeWidth
                    )

                    Text(
                        modifier = Modifier
                            .padding(themeValues.dimens.padding0, themeValues.dimens.padding10, themeValues.dimens.padding0, themeValues.dimens.padding0)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        text = "Loading",
                        color = themeValues.colors.textBlack,
                        style = themeValues.typography.textSmallBold
                    )
                })
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialoge(
    dismiss: () -> Unit,
    themeValues: AppThemeValues = rememberAppThemeValues()
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = themeValues.colors.dialogueBackground, shape = themeValues.shapes.roundedCorderBackground)
            .wrapContentWidth()
            .wrapContentHeight(),
        content = {
            Column(
                modifier = Modifier
                    .padding(themeValues.dimens.padding0, themeValues.dimens.padding10, themeValues.dimens.padding0, themeValues.dimens.padding10)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = "Something went wrong,\nPlease try again later",
                        color = themeValues.colors.textBlack,
                        style = themeValues.typography.textSmallBold
                    )

                    Text(
                        text = "Dismiss",
                        color = themeValues.colors.textBlack,
                        style = themeValues.typography.textSmallBold,
                        modifier = Modifier
                            .padding(themeValues.dimens.padding0, themeValues.dimens.padding10, themeValues.dimens.padding0, themeValues.dimens.padding0)
                            .wrapContentSize()
                            .clickable(onClick = {
                                dismiss.invoke()
                            })
                    )
                })
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDialoge(
    message: String,
    ok: () -> Unit,
    cancel: () -> Unit,
    okButtonText: String,
    cancelButtonText: String,
    themeValues: AppThemeValues = rememberAppThemeValues(),
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = themeValues.colors.dialogueBackground, shape = themeValues.shapes.roundedCorderBackground)
            .wrapContentWidth()
            .wrapContentHeight(),
        content = {
            Column(
                modifier = Modifier
                    .padding(themeValues.dimens.padding0, themeValues.dimens.padding10, themeValues.dimens.padding0, themeValues.dimens.padding0)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(themeValues.dimens.padding15, themeValues.dimens.padding10),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = message,
                        color = themeValues.colors.textBlack,
                        style = themeValues.typography.textSmallBold
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .padding(themeValues.dimens.padding0, themeValues.dimens.padding20, themeValues.dimens.padding0, themeValues.dimens.padding0)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        content = {

                            Text(
                                text = okButtonText,
                                color = themeValues.colors.textBlack,
                                style = themeValues.typography.textSmallBold,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable(onClick = {
                                        ok.invoke()
                                    })
                            )

                            Text(
                                text = cancelButtonText,
                                color = themeValues.colors.textBlack,
                                style = themeValues.typography.textSmallBold,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable(onClick = {
                                        cancel.invoke()
                                    })
                            )
                        })
                })
        })
}