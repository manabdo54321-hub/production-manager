package com.abdo.core.domain.model

import java.time.LocalDate

enum class IncomeCategory {
    SALES, ADVANCE, BONUS, OTHER
}

data class Income(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: IncomeCategory = IncomeCategory.SALES,
    val productId: Int? = null,
    val productName: String = "",
    val date: LocalDate = LocalDate.now(),
    val notes: String = ""
)
