package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.repository.FinanceRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Long> {
        if (expense.title.isBlank())
            return Result.failure(Exception("عنوان المصروف مطلوب"))
        if (expense.amount <= 0)
            return Result.failure(Exception("المبلغ يجب أن يكون أكبر من صفر"))
        return Result.success(repository.addExpense(expense))
    }
}
