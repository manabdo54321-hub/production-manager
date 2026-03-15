package com.abdo.core.domain.model

import java.time.LocalDate

data class DailyProduction(
    val date: LocalDate,
    val totalQuantity: Int,
    val totalValue: Double,
    val entries: List<ProductionEntry>
)
