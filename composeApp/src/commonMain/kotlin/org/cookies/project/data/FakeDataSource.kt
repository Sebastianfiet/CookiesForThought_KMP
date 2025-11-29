package org.cookies.project.data

import org.cookies.project.model.*

object FakeDataSource {

    fun defaultBuildings(): List<Building> = listOf(
        Building(BuildingType.CURSOR, baseCpm = 1, baseCost = 50),
        Building(BuildingType.FARM,   baseCpm = 5, baseCost = 500),
        Building(BuildingType.MINE,   baseCpm = 15, baseCost = 2_000),
        Building(BuildingType.FACTORY,baseCpm = 50, baseCost = 10_000),
        Building(BuildingType.TEMPLE, baseCpm = 150, baseCost = 50_000),
        Building(BuildingType.BANK,   baseCpm = 400, baseCost = 250_000)
    )

    fun defaultTips(): List<Tip> = listOf(
        // Técnicas de concentración (10)
        Tip("focus1", TipCategory.FOCUS_TECHNIQUES,
            "Respira 4-7-8 durante un minuto antes de empezar.", 0),
        Tip("focus2", TipCategory.FOCUS_TECHNIQUES,
            "Ten a la vista solo una tarea a la vez.", 0),
        Tip("focus3", TipCategory.FOCUS_TECHNIQUES,
            "Usa auriculares o ruido blanco para bloquear distracciones.", 500),
        Tip("focus4", TipCategory.FOCUS_TECHNIQUES,
            "Divide tareas grandes en pasos de 15–25 minutos.", 1_000),
        Tip("focus5", TipCategory.FOCUS_TECHNIQUES,
            "Anota en una hoja las ideas que te distraen para atenderlas después.", 2_000),
        Tip("focus6", TipCategory.FOCUS_TECHNIQUES,
            "Haz micro-pausas de 2–3 minutos cada 25–30 minutos.", 5_000),
        Tip("focus7", TipCategory.FOCUS_TECHNIQUES,
            "Ten agua cerca para mantenerte hidratado mientras estudias.", 10_000),
        Tip("focus8", TipCategory.FOCUS_TECHNIQUES,
            "Crea un rincón fijo solo para estudiar o trabajar.", 25_000),
        Tip("focus9", TipCategory.FOCUS_TECHNIQUES,
            "Apaga o aleja el móvil si no lo necesitas para la tarea.", 50_000),
        Tip("focus10", TipCategory.FOCUS_TECHNIQUES,
            "Cierra pestañas y apps que no aporten a la sesión actual.", 100_000),

        // Manejo del tiempo (10)
        Tip("time1", TipCategory.TIME_MANAGEMENT,
            "Empieza el día con la tarea más importante.", 0),
        Tip("time2", TipCategory.TIME_MANAGEMENT,
            "Agrupa tareas similares en bloques (emails, llamadas, etc.).", 0),
        Tip("time3", TipCategory.TIME_MANAGEMENT,
            "Reserva bloques de estudio en tu calendario como si fueran citas.", 500),
        Tip("time4", TipCategory.TIME_MANAGEMENT,
            "Define máximo 3 prioridades clave por día.", 1_000),
        Tip("time5", TipCategory.TIME_MANAGEMENT,
            "Usa listas de tareas cortas (5–7 ítems).", 2_000),
        Tip("time6", TipCategory.TIME_MANAGEMENT,
            "Elige una hora fija para revisar mensajes o redes.", 5_000),
        Tip("time7", TipCategory.TIME_MANAGEMENT,
            "Aprende a decir no a tareas que no te acercan a tus metas.", 10_000),
        Tip("time8", TipCategory.TIME_MANAGEMENT,
            "Deja un bloque de tiempo libre para imprevistos.", 25_000),
        Tip("time9", TipCategory.TIME_MANAGEMENT,
            "Revisa tu día al final y ajusta el plan del siguiente.", 50_000),
        Tip("time10", TipCategory.TIME_MANAGEMENT,
            "Organiza tus objetivos pensando en semanas, no solo en días.", 100_000)
    )

    fun defaultAchievements(): List<Achievement> = listOf(
        Achievement(
            id = "cookies_100",
            title = "Cosmic Cookies",
            description = "Consigue 100 galletas en total.",
            type = AchievementType.TOTAL_COOKIES,
            target = 100
        ),
        Achievement(
            id = "cookies_1000",
            title = "Galatic Cookies",
            description = "Consigue 1.000 galletas en total.",
            type = AchievementType.TOTAL_COOKIES,
            target = 1_000
        ),
        Achievement(
            id = "cookies_10000",
            title = "Universal Cookies",
            description = "Consigue 10.000 galletas en total.",
            type = AchievementType.TOTAL_COOKIES,
            target = 10_000
        ),
        Achievement(
            id = "cookies_100000",
            title = "TimeLess Cookies",
            description = "Consigue 100.000 galletas en total.",
            type = AchievementType.TOTAL_COOKIES,
            target = 100_000
        ),

        Achievement(
            id = "sessions_1",
            title = "Primera sesión",
            description = "Completa tu primera sesión de concentración.",
            type = AchievementType.SESSIONS_COMPLETED,
            target = 1
        ),
        Achievement(
            id = "sessions_5",
            title = "Concentration Goal I",
            description = "Completa 5 sesiones.",
            type = AchievementType.SESSIONS_COMPLETED,
            target = 5
        ),
        Achievement(
            id = "sessions_10",
            title = "Concentration Goal II",
            description = "Completa 10 sesiones.",
            type = AchievementType.SESSIONS_COMPLETED,
            target = 10
        ),
        Achievement(
            id = "sessions_25",
            title = "Concentration Goal III",
            description = "Completa 25 sesiones.",
            type = AchievementType.SESSIONS_COMPLETED,
            target = 25
        ),

        Achievement(
            id = "long_25",
            title = "Time Warp",
            description = "Completa una sesión de al menos 25 minutos.",
            type = AchievementType.LONGEST_SESSION,
            target = 25
        ),
        Achievement(
            id = "long_60",
            title = "Flux Capacitor",
            description = "Completa una sesión de al menos 60 minutos.",
            type = AchievementType.LONGEST_SESSION,
            target = 60
        ),
        Achievement(
            id = "long_120",
            title = "Time ParaDox Resolver",
            description = "Completa una sesión de al menos 120 minutos.",
            type = AchievementType.LONGEST_SESSION,
            target = 120
        )
    )

}
