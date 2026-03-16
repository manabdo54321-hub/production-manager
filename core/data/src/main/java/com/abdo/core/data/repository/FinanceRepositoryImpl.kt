package com.abdo.core.data.repository

import com.abdo.core.data.dao.ExpenseDao
import com.abdo.core.data.dao.IncomeDao
import com.abdo.core.data.mapper.toDomain
import com.abdo.core.data.mapper.toEntity
import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.FinanceSummary
import com.abdo.core.domain.model.Income
import com.abdo.core.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val incomeDao: IncomeDao
) : FinanceRepository {

    override fun getAllExpenses(): Flow<List<Expense>> =
        expenseDao.getAllExpenses().map { list -> list.map { it.toDomain() } }

    override fun getAllIncomes(): Flow<List<Income>> =
        incomeDao.getAllIncomes().map { list -> list.map { it.toDomain() } }

    override fun getMonthlyExpenses(from: LocalDate, to: LocalDate): Flow<List<Expense>> =
        expenseDao.getExpensesBetween(from, to).map { list -> list.map { it.toDomain() } }

    override fun getMonthlyIncomes(from: LocalDate, to: LocalDate): Flow<List<Income>> =
        incomeDao.getIncomesBetween(from, to).map { list -> list.map { it.toDomain() } }

    override fun getMonthlySummary(from: LocalDate, to: LocalDate): Flow<FinanceSummary> =
        combine(
            expenseDao.getExpensesBetween(from, to),
            incomeDao.getIncomesBetween(from, to)
        ) { expenses, incomes ->
            val totalExpense = expenses.sumOf { it.amount }
            val totalIncome = incomes.sumOf { it.amount }
            FinanceSummary(
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                netProfit = totalIncome - totalExpense,
                incomes = incomes.map { it.toDomain() },
                expenses = expenses.map { it.toDomain() },
                month = from.monthValue,
                year = from.year
            )
        }

    override suspend fun addExpense(expense: Expense): Long =
        expenseDao.insertExpense(expense.toEntity())

    override suspend fun deleteExpense(expense: Expense) =
        expenseDao.deleteExpense(expense.toEntity())

    override suspend fun addIncome(income: Income): Long =
        incomeDao.insertIncome(income.toEntity())

    override suspend fun deleteIncome(income: Income) =
        incomeDao.deleteIncome(income.toEntity())
}
