package org.cookies.project.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.cookies.project.data.AchievementsRepository
import org.cookies.project.data.CookiesRepository
import org.cookies.project.data.FakeDataSource
import org.cookies.project.data.SettingsRepository
import org.cookies.project.data.TipsRepository
import org.cookies.project.model.*
import org.cookies.project.persistence.KeyValueStorage

class AppState(
    private val storage: KeyValueStorage,
    val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    val cookiesRepository: CookiesRepository =
        CookiesRepository(FakeDataSource.defaultBuildings(), storage),
    val tipsRepository: TipsRepository =
        TipsRepository(FakeDataSource.defaultTips()),
    val settingsRepository: SettingsRepository =
        SettingsRepository(storage),
    val achievementsRepository: AchievementsRepository =
        AchievementsRepository(FakeDataSource.defaultAchievements())
) {
    var currentScreen: Screen by mutableStateOf(Screen.Home)
        private set

    val stats: StateFlow<Stats> = cookiesRepository.stats
    val buildings: StateFlow<List<Building>> = cookiesRepository.buildings
    val settings: StateFlow<Settings> = settingsRepository.settings
    val tips: StateFlow<List<Tip>> = tipsRepository.tips
    val achievements: StateFlow<List<Achievement>> = achievementsRepository.achievements

    // --- Sesión actual / temporizador ---
    private val _currentSession = MutableStateFlow<SessionUiState?>(null)
    val currentSession: StateFlow<SessionUiState?> = _currentSession

    private var sessionJob: Job? = null

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    // Inicia sesión con temporizador
    fun startSession(config: SessionConfig) {
        // Evitar dos sesiones simultáneas
        if (_currentSession.value != null) return

        cookiesRepository.startSession(config)

        val totalSeconds = config.durationMinutes * 60
        _currentSession.value = SessionUiState(
            config = config,
            remainingSeconds = totalSeconds,
            isRunning = true
        )

        sessionJob?.cancel()
        sessionJob = scope.launch {
            var remaining = totalSeconds
            while (remaining > 0 && isActive) {
                delay(1000)
                remaining -= 1
                _currentSession.value = _currentSession.value?.copy(remainingSeconds = remaining)
            }
            if (remaining <= 0 && isActive) {
                onSessionCompleted()
            }
        }
    }

    private fun onSessionCompleted() {
        val session = _currentSession.value ?: return
        val config = session.config

        // Producción actual
        val buildingsSnapshot = cookiesRepository.buildings.value
        val totalCpm = EconomyConfig.baseCpm +
                buildingsSnapshot.sumOf { it.baseCpm * it.owned.toLong() }

        // Galletas ganadas = CPM * minutos, con bonus x2 en extrema
        var cookiesEarned = totalCpm * config.durationMinutes.toLong()
        if (config.mode == SessionMode.EXTREME) {
            cookiesEarned *= 2
        }

        cookiesRepository.completeSession(config, cookiesEarned)

        sessionJob?.cancel()
        sessionJob = null
        _currentSession.value = null
    }

    fun cancelCurrentSession() {
        val session = _currentSession.value ?: return

        // Penalización: en modo EXTREME pierdes tus galletas actuales
        if (session.config.mode == SessionMode.EXTREME) {
            cookiesRepository.resetCurrentCookies()
        }

        sessionJob?.cancel()
        sessionJob = null
        _currentSession.value = null
    }

    fun buyBuilding(type: BuildingType) {
        cookiesRepository.buyBuilding(type)
    }

    fun setSoundOnComplete(enabled: Boolean) {
        settingsRepository.update { it.copy(soundOnComplete = enabled) }
    }

    fun setVibrateOnComplete(enabled: Boolean) {
        settingsRepository.update { it.copy(vibrateOnComplete = enabled) }
    }

    fun setSoundOnAchievement(enabled: Boolean) {
        settingsRepository.update { it.copy(soundOnAchievement = enabled) }
    }

    init {
        // Mantener logros sincronizados con las estadísticas
        scope.launch {
            cookiesRepository.stats.collect { stats ->
                achievementsRepository.updateForStats(stats)
            }
        }
    }
}
