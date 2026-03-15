package com.abdo.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val targetPerDay: Int = 0,
    val pricePerUnit: Double = 0.0,
    val isActive: Boolean = true
)
