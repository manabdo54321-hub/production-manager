package com.abdo.feature.production

import com.abdo.core.domain.model.DailyProduction
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry
import java.time.LocalDate

data class ProductionUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val dailyProduction: DailyProduction? = null,
    val history: List<ProductionEntry> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class ProductionEvent {
    data class AddEntry(
        val productId: Int,
        val quantity: Int,
        val notes: String = ""
    ) : ProductionEvent()

    data class AddProduct(
        val name: String,
        val targetPerDay: Int,
        val pricePerUnit: Double
    ) : ProductionEvent()

    data class DeleteEntry(
        val entry: ProductionEntry
    ) : ProductionEvent()

    data class SelectDate(
        val date: LocalDate
    ) : ProductionEvent()

    object ClearMessages : ProductionEvent()
}
