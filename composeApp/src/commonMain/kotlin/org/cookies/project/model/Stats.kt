package org.cookies.project.model

data class Stats(
    val currentCookies: Long = 0L,
    val totalCookies: Long = 0L,
    val sessionsStarted: Int = 0,
    val sessionsCompleted: Int = 0,
    val longestSessionMinutes: Int = 0
)
