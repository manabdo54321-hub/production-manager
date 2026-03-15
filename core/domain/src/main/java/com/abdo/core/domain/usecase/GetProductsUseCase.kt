package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Product
import com.abdo.core.domain.repository.ProductionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductionRepository
) {
    operator fun invoke(activeOnly: Boolean = true): Flow<List<Product>> {
        return if (activeOnly)
            repository.getAllActiveProducts()
        else
            repository.getAllProducts()
    }
}
