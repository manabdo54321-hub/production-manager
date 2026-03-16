package com.abdo.core.data.mapper

import com.abdo.core.data.entity.ExpenseEntity
import com.abdo.core.data.entity.IncomeEntity
import com.abdo.core.data.entity.ProductEntity
import com.abdo.core.data.entity.ProductionEntryEntity
import com.abdo.core.data.entity.TaskEntity
import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.ExpenseCategory
import com.abdo.core.domain.model.Income
import com.abdo.core.domain.model.IncomeCategory
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry
import com.abdo.core.domain.model.Task
import com.abdo.core.domain.model.TaskPriority
import com.abdo.core.domain.model.TaskStatus

// Product
fun ProductEntity.toDomain() = Product(
    id = id, name = name,
    targetPerDay = targetPerDay,
    pricePerUnit = pricePerUnit,
    isActive = isActive
)
fun Product.toEntity() = ProductEntity(
    id = id, name = name,
    targetPerDay = targetPerDay,
    pricePerUnit = pricePerUnit,
    isActive = isActive
)

// ProductionEntry
fun ProductionEntryEntity.toDomain(productName: String = "") = ProductionEntry(
    id = id, productId = productId,
    productName = productName,
    quantity = quantity, date = date, notes = notes
)
fun ProductionEntry.toEntity() = ProductionEntryEntity(
    id = id, productId = productId,
    quantity = quantity, date = date, notes = notes
)

// Task
fun TaskEntity.toDomain() = Task(
    id = id, title = title,
    description = description,
    priority = TaskPriority.valueOf(priority),
    status = TaskStatus.valueOf(status),
    dueDate = dueDate, createdAt = createdAt
)
fun Task.toEntity() = TaskEntity(
    id = id, title = title,
    description = description,
    priority = priority.name,
    status = status.name,
    dueDate = dueDate, createdAt = createdAt
)

// Expense
fun ExpenseEntity.toDomain() = Expense(
    id = id, title = title, amount = amount,
    category = ExpenseCategory.valueOf(category),
    date = date, notes = notes
)
fun Expense.toEntity() = ExpenseEntity(
    id = id, title = title, amount = amount,
    category = category.name, date = date, notes = notes
)

// Income
fun IncomeEntity.toDomain(productName: String = "") = Income(
    id = id, title = title, amount = amount,
    category = IncomeCategory.valueOf(category),
    productId = productId,
    productName = productName,
    date = date, notes = notes
)
fun Income.toEntity() = IncomeEntity(
    id = id, title = title, amount = amount,
    category = category.name,
    productId = productId,
    date = date, notes = notes
)
