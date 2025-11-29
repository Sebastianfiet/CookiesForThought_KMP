package org.cookies.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues

@Composable
fun CreditsScreen(onBack: () -> Unit, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cookies For Thought",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Créditos",
            style = MaterialTheme.typography.titleMedium
        )

        // Descripción
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Info, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = "Cookies For Thought es una app de enfoque que convierte tus minutos de concentración en recompensas. " +
                            "Elige un modo de concentración y gana galletas que reflejan tu constancia. Incluye tienda de mejoras, " +
                            "consejos prácticos y estadísticas de progreso.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Creadores
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.People, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Creadores",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text("- Sebastián Belalcázar Vivas ⚡")
                Text("- Yulieth Gabriela Jaramillo Yela")
            }
        }

        // Información de la aplicación
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Android, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Información de la aplicación",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Text("• SO mínimo recomendado: Android 8.0 (API 26) o posterior.")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Fecha de lanzamiento: 29 de noviembre de 2025")
                }
                Text("• Versión: 1.0.2")
                Text("• Plataformas: Android & Desktop (Compose Multiplatform).")
            }
        }

        Spacer(Modifier.height(8.dp))

        // Botón volver
        Button(
            onClick = onBack,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Volver")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "© 2025 Cookies For Thought",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
