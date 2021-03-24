/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.settings.Settings
import com.example.androiddevchallenge.settings.SettingsViewModel
import com.example.androiddevchallenge.weather.WeatherOverview
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

/**
 * Root Weather App Composable
 */
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun WeatherApp() {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val scope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = viewModel()

    BackdropScaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
        appBar = {
            AppBar(
                location = settingsViewModel.location.displayText,
                isBackdropOpen = scaffoldState.isRevealed,
                onBackdropToggleClick = {
                    if (scaffoldState.isRevealed) {
                        scope.launch { scaffoldState.conceal() }
                    } else {
                        scope.launch { scaffoldState.reveal() }
                    }
                }
            )
        },
        backLayerContent = { Settings() },
        frontLayerContent = { WeatherOverview() },
    )
}

@Composable
private fun AppBar(
    location: String,
    isBackdropOpen: Boolean,
    onBackdropToggleClick: () -> Unit
) {
    val toggleIcon = remember(isBackdropOpen) {
        if (isBackdropOpen) Icons.Rounded.Close else Icons.Rounded.Create
    }
    val toggleDescription = remember(isBackdropOpen) {
        if (isBackdropOpen) "Close settings" else "Open settings"
    }

    TopAppBar(
        elevation = 0.dp,
        title = {
            Text(location)
        },

        actions = {
            IconButton(onClick = onBackdropToggleClick) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = toggleDescription
                )
            }
        }
    )
}
