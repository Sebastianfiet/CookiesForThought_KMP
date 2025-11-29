package org.cookies.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.cookies.project.app.AppState
import org.cookies.project.app.CookiesForThoughtApp
import org.cookies.project.persistence.DesktopKeyValueStorage
import org.cookies.project.persistence.KeyValueStorage

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cookies For Thought"
    ) {
        val storage: KeyValueStorage = remember {
            DesktopKeyValueStorage()
        }

        val appState = remember(storage) {
            AppState(storage = storage)
        }

        CookiesForThoughtApp(appState)
    }
}
