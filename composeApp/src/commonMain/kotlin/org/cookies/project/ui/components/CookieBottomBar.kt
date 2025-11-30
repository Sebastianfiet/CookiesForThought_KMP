package org.cookies.project.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import org.cookies.project.app.AppState
import org.cookies.project.app.Screen

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)


@Composable
fun CookieBottomBar(appState: AppState) {
    val items = listOf(
        BottomNavItem(Screen.Home, "Home", Icons.Filled.Home),
        BottomNavItem(Screen.Store, "Store", Icons.Filled.ShoppingCart),
        BottomNavItem(Screen.Tips, "Tips", Icons.Filled.Lightbulb),
        BottomNavItem(Screen.Stats, "Stats", Icons.Filled.BarChart),
        BottomNavItem(Screen.Config, "Config", Icons.Filled.Settings)
    )
    val current = appState.currentScreen

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = current == item.screen,
                onClick = { appState.navigateTo(item.screen) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
