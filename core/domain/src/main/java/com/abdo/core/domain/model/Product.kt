package com.abdo.core.domain.model

data class Product(
    val id: Int = 0,
    val name: String,
    val targetPerDay: Int = 0,
    val pricePerUnit: Double = 0.0,
    val isActive: Boolean = true
)
