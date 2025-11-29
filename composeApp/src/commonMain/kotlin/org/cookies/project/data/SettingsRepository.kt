package org.cookies.project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.cookies.project.model.Settings
import org.cookies.project.persistence.KeyValueStorage

class SettingsRepository(
    private val storage: KeyValueStorage
) {
    companion object {
        private const val KEY_SOUND = "settings_sound_on_complete"
        private const val KEY_VIBRATE = "settings_vibrate_on_complete"
        private const val KEY_SOUND_ACH = "settings_sound_on_achievement"
    }

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<Settings> = _settings

    private fun loadSettings(): Settings {
        return Settings(
            soundOnComplete = storage.getBoolean(KEY_SOUND, true),
            vibrateOnComplete = storage.getBoolean(KEY_VIBRATE, true),
            soundOnAchievement = storage.getBoolean(KEY_SOUND_ACH, true)
        )
    }

    fun update(block: (Settings) -> Settings) {
        val newSettings = block(_settings.value)
        _settings.value = newSettings
        persist(newSettings)
    }

    private fun persist(s: Settings) {
        storage.putBoolean(KEY_SOUND, s.soundOnComplete)
        storage.putBoolean(KEY_VIBRATE, s.vibrateOnComplete)
        storage.putBoolean(KEY_SOUND_ACH, s.soundOnAchievement)
    }
}
