package org.cookies.project.model

data class SessionUiState(
    val config: SessionConfig,
    val remainingSeconds: Int,
    val isRunning: Boolean = true
) {
    val totalSeconds: Int get() = config.durationMinutes * 60
}
