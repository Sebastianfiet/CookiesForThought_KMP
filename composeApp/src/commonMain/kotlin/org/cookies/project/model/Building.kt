package org.cookies.project.model

data class Building(
    val type: BuildingType,
    val baseCpm: Long,
    val baseCost: Long,
    val owned: Int = 0,
)
