package com.abdo.core.data.mapper

import com.abdo.core.data.entity.ProductEntity
import com.abdo.core.data.entity.ProductionEntryEntity
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry

// Product
fun ProductEntity.toDomain() = Product(
    id = id,
    name = name,
    targetPerDay = targetPerDay,
    pricePerUnit = pricePerUnit,
    isActive = isActive
)

fun Product.toEntity() = ProductEntity(
    id = id,
    name = name,
    targetPerDay = targetPerDay,
    pricePerUnit = pricePerUnit,
    isActive = isActive
)

// ProductionEntry
fun ProductionEntryEntity.toDomain(productName: String = "") = ProductionEntry(
    id = id,
    productId = productId,
    productName = productName,
    quantity = quantity,
    date = date,
    notes = notes
)

fun ProductionEntry.toEntity() = ProductionEntryEntity(
    id = id,
    productId = productId,
    quantity = quantity,
    date = date,
    notes = notes
)
