package org.cookies.project.model

enum class AchievementType {
    TOTAL_COOKIES,
    SESSIONS_COMPLETED,
    LONGEST_SESSION
}

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val type: AchievementType,
    val target: Long,
    val unlocked: Boolean = false,
    val progress: Float = 0f
)
