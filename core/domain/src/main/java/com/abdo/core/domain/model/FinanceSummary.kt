package com.abdo.core.domain.model

import java.time.LocalDate

data class FinanceSummary(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val netProfit: Double = 0.0,
    val incomes: List<Income> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val month: Int = LocalDate.now().monthValue,
    val year: Int = LocalDate.now().year
)
