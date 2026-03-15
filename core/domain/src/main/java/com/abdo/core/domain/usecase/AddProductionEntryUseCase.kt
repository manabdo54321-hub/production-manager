package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.ProductionEntry
import com.abdo.core.domain.repository.ProductionRepository
import javax.inject.Inject

class AddProductionEntryUseCase @Inject constructor(
    private val repository: ProductionRepository
) {
    suspend operator fun invoke(entry: ProductionEntry): Result<Long> {
        if (entry.productId <= 0)
            return Result.failure(Exception("اختر منتجاً أولاً"))
        if (entry.quantity <= 0)
            return Result.failure(Exception("الكمية يجب أن تكون أكبر من صفر"))
        return Result.success(repository.addEntry(entry))
    }
}
