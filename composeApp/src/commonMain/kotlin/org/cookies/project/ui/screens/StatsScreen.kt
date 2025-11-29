package org.cookies.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import org.cookies.project.app.AppState
import org.cookies.project.model.Achievement

@Composable
fun StatsScreen(app: AppState, padding: PaddingValues) {
    val stats by app.stats.collectAsState()
    val achievements by app.achievements.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Stats & Achievements",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Estadísticas") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Logros") }
            )
        }

        Spacer(Modifier.height(16.dp))

        if (selectedTab == 0) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    StatCard(
                        label = "Cookies actuales",
                        value = stats.currentCookies.toString(),
                        icon = Icons.Filled.ShoppingCart
                    )
                }
                item {
                    StatCard(
                        label = "Cookies totales",
                        value = stats.totalCookies.toString(),
                        icon = Icons.Filled.ShoppingCart
                    )
                }
                item {
                    StatCard(
                        label = "Sesiones iniciadas",
                        value = stats.sessionsStarted.toString(),
                        icon = Icons.Filled.CheckCircle
                    )
                }
                item {
                    StatCard(
                        label = "Sesiones completadas",
                        value = stats.sessionsCompleted.toString(),
                        icon = Icons.Filled.CheckCircle
                    )
                }
                item {
                    StatCard(
                        label = "Sesión más larga",
                        value = "${stats.longestSessionMinutes} min",
                        icon = Icons.Filled.Timer
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(achievements) { ach ->
                    AchievementCard(achievement = ach)
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement
) {
    val icon = if (achievement.unlocked) {
        Icons.Filled.CheckCircle
    } else {
        Icons.Filled.Lock
    }

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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (achievement.unlocked)
                        achievement.title
                    else
                        achievement.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = achievement.description,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = achievement.progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
