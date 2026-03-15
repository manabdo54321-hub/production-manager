package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.DailyProduction
import com.abdo.core.domain.repository.ProductionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetDailyProductionUseCase @Inject constructor(
    private val repository: ProductionRepository
) {
    operator fun invoke(date: LocalDate = LocalDate.now()): Flow<DailyProduction> {
        return repository.getDailyProduction(date)
    }
}
