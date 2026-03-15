package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.ProductionEntry
import com.abdo.core.domain.repository.ProductionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetProductionHistoryUseCase @Inject constructor(
    private val repository: ProductionRepository
) {
    operator fun invoke(
        from: LocalDate? = null,
        to: LocalDate? = null
    ): Flow<List<ProductionEntry>> {
        return if (from != null && to != null)
            repository.getEntriesBetween(from, to)
        else
            repository.getAllEntries()
    }
}
