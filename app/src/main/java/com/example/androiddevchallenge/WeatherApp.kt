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
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.settings.Settings
import com.example.androiddevchallenge.settings.SettingsViewModel
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
    frontLayerContent = { WeatherContent() },
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

/**
 * Displays the weather for the user's current location
 */
@Composable
private fun WeatherContent() {
  TodaysWeatherOverview(staticTodayWeather)
}