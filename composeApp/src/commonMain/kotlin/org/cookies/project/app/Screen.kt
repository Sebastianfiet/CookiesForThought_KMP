package org.cookies.project.app

sealed class Screen {
    data object Home : Screen()
    data object Store : Screen()
    data object Tips : Screen()
    data object Stats : Screen()
    data object Config : Screen()
    data object Credits : Screen()
}
