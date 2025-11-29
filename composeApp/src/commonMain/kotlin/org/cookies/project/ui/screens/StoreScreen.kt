package org.cookies.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import org.cookies.project.app.AppState
import org.cookies.project.model.Building
import org.cookies.project.model.BuildingType
import org.cookies.project.model.EconomyConfig

@Composable
fun StoreScreen(app: AppState, padding: PaddingValues) {
    val stats by app.stats.collectAsState()
    val buildings by app.buildings.collectAsState()

    val totalCpm = EconomyConfig.baseCpm +
            buildings.sumOf { it.baseCpm * it.owned.toLong() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        // Resumen arriba
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Tienda de galletas", style = MaterialTheme.typography.titleMedium)
                Text("Galletas actuales: ${stats.currentCookies}")
                Text("Producción: ${totalCpm} galletas/min")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(buildings) { building ->
                BuildingItem(
                    building = building,
                    currentCookies = stats.currentCookies,
                    onBuy = { app.buyBuilding(building.type) }
                )
            }
        }
    }
}

@Composable
private fun BuildingItem(
    building: Building,
    currentCookies: Long,
    onBuy: () -> Unit
) {
    val price = building.baseCost * (building.owned + 1)
    val canBuy = currentCookies >= price

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono + nombre
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${buildingEmoji(building.type)} ${buildingName(building.type)}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Tiene: ${building.owned}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Producción: ${building.baseCpm} cpm por edificio",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Precio: $price",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onBuy,
                    enabled = canBuy
                ) {
                    Text("Comprar")
                }
            }
        }
    }
}

private fun buildingName(type: BuildingType): String =
    when (type) {
        BuildingType.CURSOR -> "Cursor"
        BuildingType.FARM -> "Granja"
        BuildingType.MINE -> "Mina"
        BuildingType.FACTORY -> "Fábrica"
        BuildingType.TEMPLE -> "Templo"
        BuildingType.BANK -> "Banco"
    }

private fun buildingEmoji(type: BuildingType): String =
    when (type) {
        BuildingType.CURSOR -> "🖱"
        BuildingType.FARM -> "🌾"
        BuildingType.MINE -> "⛏"
        BuildingType.FACTORY -> "🏭"
        BuildingType.TEMPLE -> "⛪"
        BuildingType.BANK -> "🏦"
    }
