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
package com.example.androiddevchallenge.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.WeatherTheme

/**
 * Settings UI, used in the main application backdrop.
 *
 * Gives the user access to their preferred weather units and location
 */
@Composable
fun Settings() {
    val viewModel: SettingsViewModel = viewModel()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("City")
        Row {
            OutlinedTextField(
                colors = onPrimaryTextFieldColors(),
                value = viewModel.location.displayText,
                onValueChange = { viewModel.updateLocation(it) }
            )
        }

        Spacer(Modifier.height(8.dp))

        Text("Units")
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            WeatherUnits.values().forEach { units ->
                UnitRadioButton(
                    modifier = Modifier.padding(4.dp),
                    units = units,
                    selected = viewModel.units == units,
                    onClick = { viewModel.units = units }
                )
            }
        }
    }
}

@Composable
private fun onPrimaryTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = MaterialTheme.colors.secondary,
    focusedBorderColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
    focusedLabelColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high)
)

/**
 * Radio button to display a single weather unit option (e.g. Imperial or Metric)
 */
@Composable
private fun UnitRadioButton(
    modifier: Modifier = Modifier,
    units: WeatherUnits,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier =
        Modifier
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            )
            .then(modifier)
    ) {
        RadioButton(
            selected = selected,
            onClick = null // handled by parent row
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            text = units.unitSamples
        )
    }
}

@Preview
@Composable
private fun SettingsPreview() {
    WeatherTheme {
        Settings()
    }
}
