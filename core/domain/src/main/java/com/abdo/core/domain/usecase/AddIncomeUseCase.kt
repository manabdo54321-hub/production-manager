package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Income
import com.abdo.core.domain.repository.FinanceRepository
import javax.inject.Inject

class AddIncomeUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(income: Income): Result<Long> {
        if (income.title.isBlank())
            return Result.failure(Exception("عنوان الدخل مطلوب"))
        if (income.amount <= 0)
            return Result.failure(Exception("المبلغ يجب أن يكون أكبر من صفر"))
        return Result.success(repository.addIncome(income))
    }
}
