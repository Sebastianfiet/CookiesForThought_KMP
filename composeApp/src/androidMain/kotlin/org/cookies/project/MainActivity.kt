package org.cookies.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import org.cookies.project.app.AppState
import org.cookies.project.app.CookiesForThoughtApp
import org.cookies.project.persistence.AndroidKeyValueStorage
import org.cookies.project.persistence.KeyValueStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val storage: KeyValueStorage = remember {
                AndroidKeyValueStorage(this@MainActivity)
            }

            val appState = remember(storage) {
                AppState(storage = storage)
            }

            CookiesForThoughtApp(appState)
        }
    }
}
