package org.cookies.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import org.cookies.project.app.AppState
import org.cookies.project.model.Tip
import org.cookies.project.model.TipCategory

@Composable
fun TipsScreen(app: AppState, padding: PaddingValues) {
    val stats by app.stats.collectAsState()
    val tips by app.tips.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    val categories = listOf(
        TipCategory.FOCUS_TECHNIQUES,
        TipCategory.TIME_MANAGEMENT
    )
    val titles = listOf("Técnicas de concentración", "Manejo del tiempo")

    val selectedCategory = categories[selectedTab]
    val filteredTips = tips.filter { it.category == selectedCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Consejos",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Galletas totales: ${stats.totalCookies}",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredTips) { tip ->
                TipItem(
                    tip = tip,
                    totalCookies = stats.totalCookies
                )
            }
        }
    }
}

@Composable
private fun TipItem(
    tip: Tip,
    totalCookies: Long
) {
    val unlocked = totalCookies >= tip.requiredCookies

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
            Text(
                text = if (unlocked) "Tip" else "Tip bloqueado 🔒",
                style = MaterialTheme.typography.titleSmall
            )

            if (unlocked) {
                Text(
                    text = tip.text,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "Desbloquea este consejo al alcanzar ${tip.requiredCookies} galletas totales.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
