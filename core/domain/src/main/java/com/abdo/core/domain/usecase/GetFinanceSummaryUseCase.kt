package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.FinanceSummary
import com.abdo.core.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class GetFinanceSummaryUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    operator fun invoke(
        month: Int = LocalDate.now().monthValue,
        year: Int = LocalDate.now().year
    ): Flow<FinanceSummary> {
        val from = LocalDate.of(year, month, 1)
        val to = from.with(TemporalAdjusters.lastDayOfMonth())
        return repository.getMonthlySummary(from, to)
    }
}
