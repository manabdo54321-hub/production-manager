package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Product
import com.abdo.core.domain.repository.ProductionRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductionRepository
) {
    suspend operator fun invoke(product: Product): Result<Long> {
        if (product.name.isBlank())
            return Result.failure(Exception("اسم المنتج مطلوب"))
        if (product.targetPerDay < 0)
            return Result.failure(Exception("الهدف اليومي لا يمكن أن يكون سالباً"))
        if (product.pricePerUnit < 0)
            return Result.failure(Exception("السعر لا يمكن أن يكون سالباً"))
        return Result.success(repository.addProduct(product))
    }
}
