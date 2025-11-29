package org.cookies.project.model

data class Tip(
    val id: String,
    val category: TipCategory,
    val text: String,
    val requiredCookies: Long = 0L   // 0 = desbloqueado por defecto
)
