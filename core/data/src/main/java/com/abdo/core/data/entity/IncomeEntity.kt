package com.abdo.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "incomes")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String = "SALES",
    val productId: Int? = null,
    val date: LocalDate = LocalDate.now(),
    val notes: String = ""
)
