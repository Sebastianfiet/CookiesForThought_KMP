package org.cookies.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import org.cookies.project.app.AppState
import org.cookies.project.model.*

@Composable
fun HomeScreen(app: AppState, padding: PaddingValues) {
    val stats by app.stats.collectAsState()
    val buildings by app.buildings.collectAsState()

    // 👇 aquí el cambio importante
    val sessionState = app.currentSession.collectAsState()
    val session = sessionState.value

    val totalCpm = EconomyConfig.baseCpm +
            buildings.sumOf { it.baseCpm * it.owned.toLong() }

    var minutes by remember { mutableStateOf(25f) }
    var mode by remember { mutableStateOf(SessionMode.NORMAL) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stats.currentCookies} galletas",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "%.2f galletas/min".format(totalCpm.toDouble()),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "🍪",
            fontSize = 72.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "¡Las galletas te esperan!",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "¿Cómo planeas concentrarte hoy?",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(32.dp))

        if (session == null) {
            // --- Configurar nueva sesión ---
            Text(
                text = "Duración (min)",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${minutes.toInt()} min",
                style = MaterialTheme.typography.titleLarge
            )
            Slider(
                value = minutes,
                onValueChange = { minutes = it },
                valueRange = 5f..180f,
                steps = (180 - 5) - 1
            )

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isNormal = mode == SessionMode.NORMAL
                val isExtreme = mode == SessionMode.EXTREME

                if (isNormal) {
                    Button(onClick = { /* ya seleccionado */ }) {
                        Text("Concentración normal")
                    }
                    OutlinedButton(onClick = { mode = SessionMode.EXTREME }) {
                        Text("Concentración extrema")
                    }
                } else if (isExtreme) {
                    OutlinedButton(onClick = { mode = SessionMode.NORMAL }) {
                        Text("Concentración normal")
                    }
                    Button(onClick = { /* ya seleccionado */ }) {
                        Text("Concentración extrema")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val config = SessionConfig(
                        durationMinutes = minutes.toInt(),
                        mode = mode
                    )
                    app.startSession(config)
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Iniciar sesión de concentración")
            }
        } else {
            SessionInProgressSection(
                ui = session,
                onCancel = { app.cancelCurrentSession() }
            )
        }
    }
}

@Composable
private fun SessionInProgressSection(
    ui: SessionUiState,
    onCancel: () -> Unit
) {
    val remaining = ui.remainingSeconds
    val minutes = remaining / 60
    val seconds = remaining % 60

    Spacer(Modifier.height(16.dp))

    Text(
        text = if (ui.config.mode == SessionMode.EXTREME)
            "Sesión extrema en progreso"
        else
            "Sesión en progreso",
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = String.format("%02d:%02d restantes", minutes, seconds),
        style = MaterialTheme.typography.headlineMedium
    )

    Spacer(Modifier.height(16.dp))

    Text(
        text = "Duración total: ${ui.config.durationMinutes} min",
        style = MaterialTheme.typography.bodySmall
    )

    Spacer(Modifier.height(24.dp))

    Button(
        onCancel,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        val label = if (ui.config.mode == SessionMode.EXTREME)
            "Abortar (pierdes tus galletas)"
        else
            "Cancelar sesión"
        Text(label)
    }
}
