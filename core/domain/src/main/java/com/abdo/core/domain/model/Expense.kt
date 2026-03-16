package com.abdo.core.domain.model

import java.time.LocalDate

enum class ExpenseCategory {
    RAW_MATERIALS, TOOLS, MAINTENANCE, SALARY, UTILITIES, OTHER
}

data class Expense(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory = ExpenseCategory.OTHER,
    val date: LocalDate = LocalDate.now(),
    val notes: String = ""
)
