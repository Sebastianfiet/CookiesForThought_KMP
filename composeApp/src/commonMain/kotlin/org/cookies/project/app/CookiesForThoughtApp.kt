package org.cookies.project.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import org.cookies.project.ui.components.CookieBottomBar
import org.cookies.project.ui.components.CookieTopBar
import org.cookies.project.ui.screens.ConfigScreen
import org.cookies.project.ui.screens.CreditsScreen
import org.cookies.project.ui.screens.HomeScreen
import org.cookies.project.ui.screens.StatsScreen
import org.cookies.project.ui.screens.StoreScreen
import org.cookies.project.ui.screens.TipsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CookiesForThoughtApp(app: AppState) {
    MaterialTheme {
        Scaffold(
            topBar = { CookieTopBar(app) },
            bottomBar = { CookieBottomBar(app) }
        ) { padding ->

            AnimatedContent(
                targetState = app.currentScreen,
                transitionSpec = {
                    // Siempre entra desde la derecha y sale hacia la izquierda
                    (slideInHorizontally { fullWidth -> fullWidth } + fadeIn())
                        .togetherWith(
                            slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                        )
                },
                label = "ScreenAnimatedContent"
            ) { screen ->
                when (screen) {
                    Screen.Home -> HomeScreen(app, padding)
                    Screen.Store -> StoreScreen(app, padding)
                    Screen.Tips -> TipsScreen(app, padding)
                    Screen.Stats -> StatsScreen(app, padding)
                    Screen.Config -> ConfigScreen(app, padding)
                    Screen.Credits -> CreditsScreen(
                        onBack = { app.navigateTo(Screen.Config) },
                        padding = padding
                    )
                }

            }
        }
    }
}
