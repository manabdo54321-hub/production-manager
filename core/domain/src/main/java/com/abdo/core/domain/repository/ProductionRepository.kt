package com.abdo.core.domain.repository

import com.abdo.core.domain.model.DailyProduction
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProductionRepository {

    // Products
    fun getAllActiveProducts(): Flow<List<Product>>
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getProductById(id: Int): Product?
    suspend fun addProduct(product: Product): Long
    suspend fun updateProduct(product: Product)
    suspend fun deactivateProduct(id: Int)

    // Production Entries
    fun getEntriesByDate(date: LocalDate): Flow<List<ProductionEntry>>
    fun getEntriesBetween(from: LocalDate, to: LocalDate): Flow<List<ProductionEntry>>
    fun getAllEntries(): Flow<List<ProductionEntry>>
    fun getDailyProduction(date: LocalDate): Flow<DailyProduction>
    suspend fun addEntry(entry: ProductionEntry): Long
    suspend fun updateEntry(entry: ProductionEntry)
    suspend fun deleteEntry(entry: ProductionEntry)
}
