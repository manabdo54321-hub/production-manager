package com.abdo.core.domain.repository

import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.FinanceSummary
import com.abdo.core.domain.model.Income
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FinanceRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getAllIncomes(): Flow<List<Income>>
    fun getMonthlyExpenses(from: LocalDate, to: LocalDate): Flow<List<Expense>>
    fun getMonthlyIncomes(from: LocalDate, to: LocalDate): Flow<List<Income>>
    fun getMonthlySummary(from: LocalDate, to: LocalDate): Flow<FinanceSummary>
    suspend fun addExpense(expense: Expense): Long
    suspend fun deleteExpense(expense: Expense)
    suspend fun addIncome(income: Income): Long
    suspend fun deleteIncome(income: Income)
}
