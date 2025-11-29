package org.cookies.project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.cookies.project.model.*
import org.cookies.project.persistence.KeyValueStorage

class CookiesRepository(
    initialBuildings: List<Building>,
    private val storage: KeyValueStorage
) {
    companion object {
        private const val KEY_CURRENT = "stats_currentCookies"
        private const val KEY_TOTAL = "stats_totalCookies"
        private const val KEY_SESSIONS_STARTED = "stats_sessionsStarted"
        private const val KEY_SESSIONS_COMPLETED = "stats_sessionsCompleted"
        private const val KEY_LONGEST_SESSION = "stats_longestSession"
    }

    private val _stats = MutableStateFlow(loadStats())
    val stats: StateFlow<Stats> = _stats

    private val _buildings = MutableStateFlow(loadBuildings(initialBuildings))
    val buildings: StateFlow<List<Building>> = _buildings

    private fun loadStats(): Stats {
        return Stats(
            currentCookies = storage.getLong(KEY_CURRENT, 0L),
            totalCookies = storage.getLong(KEY_TOTAL, 0L),
            sessionsStarted = storage.getInt(KEY_SESSIONS_STARTED, 0),
            sessionsCompleted = storage.getInt(KEY_SESSIONS_COMPLETED, 0),
            longestSessionMinutes = storage.getInt(KEY_LONGEST_SESSION, 0)
        )
    }

    private fun loadBuildings(initial: List<Building>): List<Building> {
        return initial.map { b ->
            val keyOwned = "building_${b.type.name}_owned"
            val owned = storage.getInt(keyOwned, b.owned)
            b.copy(owned = owned)
        }
    }

    private fun persistStats() {
        val s = _stats.value
        storage.putLong(KEY_CURRENT, s.currentCookies)
        storage.putLong(KEY_TOTAL, s.totalCookies)
        storage.putInt(KEY_SESSIONS_STARTED, s.sessionsStarted)
        storage.putInt(KEY_SESSIONS_COMPLETED, s.sessionsCompleted)
        storage.putInt(KEY_LONGEST_SESSION, s.longestSessionMinutes)
    }

    private fun persistBuildings() {
        _buildings.value.forEach { b ->
            val keyOwned = "building_${b.type.name}_owned"
            storage.putInt(keyOwned, b.owned)
        }
    }

    // --- API pública igual que antes, pero llamando a persistX() ---

    fun startSession(config: SessionConfig) {
        val current = _stats.value
        _stats.value = current.copy(
            sessionsStarted = current.sessionsStarted + 1
        )
        persistStats()
    }

    fun completeSession(config: SessionConfig, cookiesEarned: Long) {
        val current = _stats.value
        _stats.value = current.copy(
            sessionsCompleted = current.sessionsCompleted + 1,
            currentCookies = current.currentCookies + cookiesEarned,
            totalCookies = current.totalCookies + cookiesEarned,
            longestSessionMinutes = maxOf(
                current.longestSessionMinutes,
                config.durationMinutes
            )
        )
        persistStats()
    }

    fun buyBuilding(type: BuildingType) {
        val currentStats = _stats.value
        val currentBuildings = _buildings.value

        val target = currentBuildings.firstOrNull { it.type == type } ?: return
        val owned = target.owned
        val price = target.baseCost * (owned + 1)

        if (currentStats.currentCookies < price) return

        _stats.value = currentStats.copy(
            currentCookies = currentStats.currentCookies - price
        )
        _buildings.value = currentBuildings.map { b ->
            if (b.type == type) b.copy(owned = b.owned + 1) else b
        }

        persistStats()
        persistBuildings()
    }

    fun resetCurrentCookies() {
        val current = _stats.value
        _stats.value = current.copy(currentCookies = 0L)
        persistStats()
    }
}
