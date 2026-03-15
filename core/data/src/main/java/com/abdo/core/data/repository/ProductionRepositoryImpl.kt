package com.abdo.core.data.repository

import com.abdo.core.data.dao.ProductDao
import com.abdo.core.data.dao.ProductionEntryDao
import com.abdo.core.data.mapper.toDomain
import com.abdo.core.data.mapper.toEntity
import com.abdo.core.domain.model.DailyProduction
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry
import com.abdo.core.domain.repository.ProductionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ProductionRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val productionEntryDao: ProductionEntryDao
) : ProductionRepository {

    // Products
    override fun getAllActiveProducts(): Flow<List<Product>> =
        productDao.getAllActiveProducts().map { list -> list.map { it.toDomain() } }

    override fun getAllProducts(): Flow<List<Product>> =
        productDao.getAllProducts().map { list -> list.map { it.toDomain() } }

    override suspend fun getProductById(id: Int): Product? =
        productDao.getProductById(id)?.toDomain()

    override suspend fun addProduct(product: Product): Long =
        productDao.insertProduct(product.toEntity())

    override suspend fun updateProduct(product: Product) =
        productDao.updateProduct(product.toEntity())

    override suspend fun deactivateProduct(id: Int) =
        productDao.deactivateProduct(id)

    // Production Entries
    override fun getEntriesByDate(date: LocalDate): Flow<List<ProductionEntry>> =
        combine(
            productionEntryDao.getEntriesByDate(date),
            productDao.getAllProducts()
        ) { entries, products ->
            entries.map { entry ->
                val productName = products.find { it.id == entry.productId }?.name ?: ""
                entry.toDomain(productName)
            }
        }

    override fun getEntriesBetween(from: LocalDate, to: LocalDate): Flow<List<ProductionEntry>> =
        combine(
            productionEntryDao.getEntriesBetween(from, to),
            productDao.getAllProducts()
        ) { entries, products ->
            entries.map { entry ->
                val productName = products.find { it.id == entry.productId }?.name ?: ""
                entry.toDomain(productName)
            }
        }

    override fun getAllEntries(): Flow<List<ProductionEntry>> =
        combine(
            productionEntryDao.getAllEntries(),
            productDao.getAllProducts()
        ) { entries, products ->
            entries.map { entry ->
                val productName = products.find { it.id == entry.productId }?.name ?: ""
                entry.toDomain(productName)
            }
        }

    override fun getDailyProduction(date: LocalDate): Flow<DailyProduction> =
        combine(
            productionEntryDao.getEntriesByDate(date),
            productDao.getAllProducts()
        ) { entries, products ->
            val domainEntries = entries.map { entry ->
                val product = products.find { it.id == entry.productId }
                entry.toDomain(product?.name ?: "")
            }
            val totalValue = domainEntries.sumOf { entry ->
                val product = products.find { it.id == entry.productId }
                entry.quantity * (product?.pricePerUnit ?: 0.0)
            }
            DailyProduction(
                date = date,
                totalQuantity = domainEntries.sumOf { it.quantity },
                totalValue = totalValue,
                entries = domainEntries
            )
        }

    override suspend fun addEntry(entry: ProductionEntry): Long =
        productionEntryDao.insertEntry(entry.toEntity())

    override suspend fun updateEntry(entry: ProductionEntry) =
        productionEntryDao.updateEntry(entry.toEntity())

    override suspend fun deleteEntry(entry: ProductionEntry) =
        productionEntryDao.deleteEntry(entry.toEntity())
}
