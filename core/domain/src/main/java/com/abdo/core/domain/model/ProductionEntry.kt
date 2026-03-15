package com.abdo.core.domain.model

import java.time.LocalDate

data class ProductionEntry(
    val id: Int = 0,
    val productId: Int,
    val productName: String = "",
    val quantity: Int,
    val date: LocalDate = LocalDate.now(),
    val notes: String = ""
)
