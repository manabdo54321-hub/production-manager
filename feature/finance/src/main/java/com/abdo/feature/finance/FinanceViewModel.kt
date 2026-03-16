package com.abdo.feature.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.Income
import com.abdo.core.domain.repository.FinanceRepository
import com.abdo.core.domain.usecase.AddExpenseUseCase
import com.abdo.core.domain.usecase.AddIncomeUseCase
import com.abdo.core.domain.usecase.GetFinanceSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getFinanceSummaryUseCase: GetFinanceSummaryUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val financeRepository: FinanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FinanceUiState())
    val uiState: StateFlow<FinanceUiState> = _uiState.asStateFlow()

    init {
        loadSummary()
    }

    fun onEvent(event: FinanceEvent) {
        when (event) {
            is FinanceEvent.AddExpense -> addExpense(event)
            is FinanceEvent.AddIncome -> addIncome(event)
            is FinanceEvent.DeleteExpense -> deleteExpense(event.expense)
            is FinanceEvent.DeleteIncome -> deleteIncome(event.income)
            is FinanceEvent.ChangeMonth -> {
                _uiState.update {
                    it.copy(selectedMonth = event.month, selectedYear = event.year)
                }
                loadSummary(event.month, event.year)
            }
            is FinanceEvent.ClearMessages ->
                _uiState.update { it.copy(errorMessage = null, successMessage = null) }
        }
    }

    private fun loadSummary(
        month: Int = _uiState.value.selectedMonth,
        year: Int = _uiState.value.selectedYear
    ) {
        viewModelScope.launch {
            getFinanceSummaryUseCase(month, year).collect { summary ->
                _uiState.update { it.copy(summary = summary) }
            }
        }
    }

    private fun addExpense(event: FinanceEvent.AddExpense) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val expense = Expense(
                title = event.title,
                amount = event.amount,
                category = event.category,
                notes = event.notes
            )
            addExpenseUseCase(expense)
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, successMessage = "تم إضافة المصروف ✓")
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message)
                    }
                }
        }
    }

    private fun addIncome(event: FinanceEvent.AddIncome) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val income = Income(
                title = event.title,
                amount = event.amount,
                category = event.category,
                notes = event.notes
            )
            addIncomeUseCase(income)
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, successMessage = "تم إضافة الدخل ✓")
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message)
                    }
                }
        }
    }

    private fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            financeRepository.deleteExpense(expense)
        }
    }

    private fun deleteIncome(income: Income) {
        viewModelScope.launch {
            financeRepository.deleteIncome(income)
        }
    }
}
