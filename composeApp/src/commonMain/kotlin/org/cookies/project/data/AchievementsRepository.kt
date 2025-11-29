package org.cookies.project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.cookies.project.model.Achievement
import org.cookies.project.model.AchievementType
import org.cookies.project.model.Stats

class AchievementsRepository(
    initial: List<Achievement>
) {
    private val _achievements = MutableStateFlow(initial)
    val achievements: StateFlow<List<Achievement>> = _achievements

    fun updateForStats(stats: Stats) {
        _achievements.value = _achievements.value.map { ach ->
            val value = when (ach.type) {
                AchievementType.TOTAL_COOKIES -> stats.totalCookies.toLong()
                AchievementType.SESSIONS_COMPLETED -> stats.sessionsCompleted.toLong()
                AchievementType.LONGEST_SESSION -> stats.longestSessionMinutes.toLong()
            }
            val unlocked = value >= ach.target
            val progress = (value.toFloat() / ach.target)
                .coerceIn(0f, 1f)
            ach.copy(unlocked = unlocked, progress = progress)
        }
    }
}
