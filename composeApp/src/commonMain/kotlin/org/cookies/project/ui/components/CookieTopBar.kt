package org.cookies.project.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.cookies.project.app.AppState
import org.cookies.project.app.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookieTopBar(appState: AppState) {
    val currentScreen = appState.currentScreen

    val title = when (currentScreen) {
        Screen.Home -> "Cookie Time"
        Screen.Store -> "Cookie Store"
        Screen.Tips -> "Focus Tips"
        Screen.Stats -> "Your Stats"
        Screen.Config -> "Settings"
        Screen.Credits -> "Credits"
    }

    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            if (currentScreen == Screen.Config) {
                IconButton(onClick = { appState.navigateTo(Screen.Credits) }) {
                    Text("ⓘ")
                }
            }
        }
    )
}
