package com.abdo.feature.finance

import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.ExpenseCategory
import com.abdo.core.domain.model.FinanceSummary
import com.abdo.core.domain.model.Income
import com.abdo.core.domain.model.IncomeCategory

data class FinanceUiState(
    val isLoading: Boolean = false,
    val summary: FinanceSummary = FinanceSummary(),
    val selectedMonth: Int = java.time.LocalDate.now().monthValue,
    val selectedYear: Int = java.time.LocalDate.now().year,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class FinanceEvent {
    data class AddExpense(
        val title: String,
        val amount: Double,
        val category: ExpenseCategory,
        val notes: String = ""
    ) : FinanceEvent()

    data class AddIncome(
        val title: String,
        val amount: Double,
        val category: IncomeCategory,
        val notes: String = ""
    ) : FinanceEvent()

    data class DeleteExpense(val expense: Expense) : FinanceEvent()
    data class DeleteIncome(val income: Income) : FinanceEvent()
    data class ChangeMonth(val month: Int, val year: Int) : FinanceEvent()
    object ClearMessages : FinanceEvent()
}
